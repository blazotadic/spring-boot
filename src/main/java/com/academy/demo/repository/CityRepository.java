package com.academy.demo.repository;

import com.academy.demo.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>
{
    // SELECT city.*
    // FROM city
    // JOIN country ON city.country_id = country.id
    // WHERE country.short_code = 'ME';
    List<City> findByCountryShortCode(String shortCode);

    // SELECT city.*
    // FROM city
    // JOIN country ON city.country_id = country.id
    // WHERE country.short_code = 'ME' AND city.name LIKE '?%';
    List<City> findByCountryShortCodeAndNameStartingWith(String shortCode, String namePart);

    // SELECT city.*
    // FROM city
    // JOIN country ON city.country_id = country.id
    // WHERE country.short_code = 'ME' AND country.name like '?%';
    List<City> findByCountryShortCodeAndCountryNameStartingWith(String shortCode, String countryName);

    @Query(value = "select city from City as city " +
            "join fetch city.country as country " +
            "where country.shortCode = :shortCode")
    List<City> findAllCitiesFromGivenCountry(@Param("shortCode") String countryShortCode);
    // SELECT city.*
    // FROM city
    // JOIN country ON city.country_id = country.id
    // WHERE country.short_code = 'ME';

    @Query(value = "select city from City city " +
            "join fetch city.country")
    List<City> findAllWithCountries();
}
