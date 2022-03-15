package com.academy.demo.repository;

import com.academy.demo.entity.Customer;
import com.academy.demo.projection.CustomerProjection;
import com.academy.demo.projection.CustomerWithAddressProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer>
{
    @Query(value = "select customer from Customer customer where customer.phone like concat(:phonePart, '%')")
    Page<Customer> findByPhoneStartingWith(@Param("phonePart") String phoneStartingPart, Pageable pageable);

    @Query(value = "select customer.id from Customer customer")
    Page<Integer> findCustomerIds(Pageable pageable);

    @Query(value = "select distinct customer from Customer customer " +
            "join fetch customer.addresses " +
            "where customer.id in (:customerIds)")
    List<Customer> findAllByIdIn(@Param("customerIds") List<Integer> customerIds);

    @Query(value = "select customer.full_name as fullName, " +
            "customer.email as email, " +
            "city.name as cityName, " +
            "country.name as countryName, " +
            "address.street as addressStreet " +
            "from customer " +
            "join customer_address on customer_address.customer_id = customer.id " +
            "join address on customer_address.address_id = address.id " +
            "join city on address.city_id = city.id " +
            "join country on city.country_id = country.id " +
            "where city.name = :cityName", nativeQuery = true)
    List<CustomerWithAddressProjection> findByCityNameWithAddressProjection(@Param("cityName") String cityName);

    @Query(value = "select customer from Customer customer " +
            "join customer.addresses address " +
            "join address.city city " +
            "join city.country country " +
            "where country.shortCode = 'CH'")
    Page<Customer> findByAddressesCityCountryName(Pageable pageable);

    @Query(value = "select customer.full_name as fullName, " +
            "customer.email as email, " +
            "city.name as cityName, " +
            "country.name as countryName, " +
            "address.street as addressStreet " +
            "from customer " +
            "join customer_address on customer_address.customer_id = customer.id " +
            "join address on customer_address.address_id = address.id " +
            "join city on address.city_id = city.id " +
            "join country on city.country_id = country.id " +
            "where country.short_code = 'CH'",
            countQuery = "select count(*) from customer " +
            "join customer_address on customer_address.customer_id = customer.id " +
            "join address on customer_address.address_id = address.id " +
            "join city on address.city_id = city.id " +
            "join country on city.country_id = country.id " +
            "where country.short_code = 'CH'",
            nativeQuery = true)
    Page<CustomerWithAddressProjection> findByAddressesCityCountryNameUsingNative(Pageable pageable);

    @Query(value = "select customer.id from Customer customer where customer.phone = :phone")
    Integer findIdByPhone(@Param("phone") String phoneNumber);

    @Query(value = "select function('upper', customer.fullName) from Customer customer")
    List<String> findAllCustomersWithUpperName();
    // UPPER(?) -> LOWER(?)

    @Procedure("get_all_customers")
    List<CustomerProjection> findAllCustomersUsingStoredProcedure();

    @Procedure("get_all_customers_by_email_domain")
    List<CustomerProjection> findAllCustomersByEmailPartUsingStoredProcedure(@Param("emailDomain") String emailDomain);

    @Procedure(procedureName = "get_customer_id_by_phone_number", outputParameterName = "customerId")
    Integer findIdByPhoneNumberUsingStoredProcedure(@Param("phoneNumber") String phoneNumber);

    @Query(value = "select customer from Customer customer " +
            "where (:fullName is null or customer.fullName = :fullName) and " +
            "(:email is null or customer.email = :email) and " +
            "(:phone is null or customer.phone = :phone)")
    List<Customer> searchAllCustomers(
        @Param("fullName") String fullName,
        @Param("email") String email,
        @Param("phone") String phone
    );
}
