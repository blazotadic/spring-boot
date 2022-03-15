package com.academy.demo.specs;

import com.academy.demo.entity.Address;
import com.academy.demo.entity.City;
import com.academy.demo.entity.Country;
import com.academy.demo.entity.Customer;
import com.academy.demo.search.CustomerSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomerSearchSpecification implements Specification<Customer> {

    private final CustomerSearch customerSearch;

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
    {
        List<Predicate> predicates = new ArrayList<>();
        Join<Customer, Address> addressJoin = fetchRelations(root);

        filterByFullName(root, criteriaBuilder, predicates);
        filterByEmail(root, criteriaBuilder, predicates);
        filterByPhone(root, criteriaBuilder, predicates);
        filterByStreet(criteriaBuilder, predicates, addressJoin);

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private Join<Customer, Address> fetchRelations(Root<Customer> root)
    {
        Fetch<Customer, Address> addressFetch = root.fetch("addresses", JoinType.LEFT);
        Join<Customer, Address> addressJoin = (Join<Customer, Address>) addressFetch;

        Fetch<Address, City> cityFetch = addressJoin.fetch("city");
        Join<Address, City> cityJoin = (Join<Address, City>) cityFetch;

        Fetch<City, Country> countryFetch = cityJoin.fetch("country");
        Join<City, Country> countryJoin = (Join<City, Country>) countryFetch;

        return addressJoin;
    }

    private void filterByStreet(CriteriaBuilder criteriaBuilder,
                                List<Predicate> predicates,
                                Join<Customer, Address> addressJoin)
    {
        if (customerSearch.getAddressStreet() != null)
        {
            Predicate addressStreetPredicate = criteriaBuilder.equal(
                addressJoin.get("street"),
                customerSearch.getAddressStreet()
            );
            predicates.add(addressStreetPredicate);
        }
    }

    private void filterByPhone(Root<Customer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates)
    {
        if (customerSearch.getPhone() != null)
        {
            Predicate phonePredicate = criteriaBuilder.equal(root.get("phone"), customerSearch.getPhone());
            predicates.add(phonePredicate);
        }
    }

    private void filterByEmail(Root<Customer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates)
    {
        if (customerSearch.getEmail() != null)
        {
            Predicate emailPredicate = criteriaBuilder.like(root.get("email"), "%" + customerSearch.getEmail() + "%");
            predicates.add(emailPredicate);
        }
    }

    private void filterByFullName(Root<Customer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates)
    {
        if (customerSearch.getFullName() != null)
        {
            Predicate fullNamePredicate = criteriaBuilder.equal(root.get("fullName"), customerSearch.getFullName());
            predicates.add(fullNamePredicate);
        }
    }
}
