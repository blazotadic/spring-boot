package com.academy.demo;

import com.academy.demo.entity.City;
import com.academy.demo.entity.Country;
import com.academy.demo.repository.CityRepository;
import com.academy.demo.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = DemoAcademyApplication.class)
public class CityIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityIntegrationTest.class);

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;


    @Test
    public void getAllCitiesFromGivenCountryTest()
    {
        List<City> cities = cityRepository.findAllCitiesFromGivenCountry("ME");
        LOGGER.info("Cities: {}", cities);
    }

    @Test
    @Transactional
    public void getOneCityByIdTest()
    {
        Optional<City> cityOptional = cityRepository.findById(2);
        if (cityOptional.isPresent())
        {
            City city = cityOptional.get(); // country inicijalno ne postoji!
            Country country = city.getCountry(); // SELECT country.* FROM country WHERE id = ?
        }
    }

    @Test
    @Transactional
    public void getAllCities()
    {
        List<City> cities = cityRepository.findAllWithCountries();
        for (City city : cities)
        {
            Country country = city.getCountry(); // no query here!
        }
    }

    // BEGIN TRANSACTION
    // 1. 100EUR (-50EUR) UPDATE
    // 2. 0EUR (ERROR) UPDATE (+50)
    // 3. ROLLBACK

    @Test
    @Transactional
    public void insertParisTest()
    {
        Country country = countryRepository.getById(1); // NO-SELECT
        // country.setId(1); // Crna Gora
//        country.setName("France");
//        country.setShortCode("FR");

        City city = new City();
        city.setName("Marseille");
        city.setCountry(country);

        // error -> country_id

        cityRepository.save(city);
    }
}
