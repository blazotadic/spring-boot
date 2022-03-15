package com.academy.demo.repository;

import com.academy.demo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>
{
    List<Address> findByPostalCode(String postalCode);
    List<Address> findByCityName(String cityName);
    List<Address> findByCityCountryShortCode(String countryShortCode);

    @Query(value = "select address from Address address where address.postalCode = :postalCode")
    List<Address> findByPostalCodeJpql(@Param("postalCode") String postalCode);

    @Query(value = "select address from Address address " +
            "join address.city city " +
            "where city.name = :cityName")
    List<Address> findByCityNameJpql(@Param("cityName") String cityName);

    @Query(value = "select address from Address address " +
            "join fetch address.city city " +
            "join fetch city.country country " +
            "where country.shortCode = :countryShortCode")
    List<Address> findByCountryShortCodeJpql(@Param("countryShortCode") String countryShortCode);

    @Query(value = "select address from Address address " +
            "join fetch address.city " +
            "where address.id = :id")
    Address findByIdWithCity(@Param("id") Integer id);
}
