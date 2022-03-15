package com.academy.demo;

import com.academy.demo.dto.CountryDTO;
import com.academy.demo.entity.Country;
import com.academy.demo.projection.CountryIdAndShortCodeProjection;
import com.academy.demo.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.Tuple;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DemoAcademyApplication.class)
public class CountryIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryIntegrationTest.class);

    @Autowired
    private CountryRepository countryRepository;


    @Test
    public void insertIndiaTest()
    {
        Country country = new Country();
        country.setName("India");
        country.setShortCode("IN");

        Country storedCountry = countryRepository.save(country);
        assertThat(storedCountry).isNotNull();
        assertThat(storedCountry.getId()).isNotNull();
        assertThat(storedCountry.getName()).isEqualTo(country.getName());
        assertThat(storedCountry.getShortCode()).isEqualTo(country.getShortCode());
    }

    @Test
    public void insertChinaTest()
    {
        Country country = new Country();
        country.setName("China");
        country.setShortCode("CH");

        Country storedCountry = countryRepository.save(country);
        assertThat(storedCountry).isNotNull();
        assertThat(storedCountry.getId()).isNotNull();
        assertThat(storedCountry.getName()).isEqualTo(country.getName());
        assertThat(storedCountry.getShortCode()).isEqualTo(country.getShortCode());
    }

    @Test
    public void findIdAndShortCodeWithConstructorMappingTest()
    {
        List<CountryDTO> countries = countryRepository.findIdAndShortCode();
        LOGGER.info("Countries: {}", countries);
    }

    @Test
    public void findNameAndShortCodeWithConstructorMappingTest()
    {
        List<CountryDTO> countries = countryRepository.findNameAndShortCode();
        LOGGER.info("Countries: {}", countries);
    }

    @Test
    public void findIdAndShortCodeUsingTupleTest()
    {
        List<Tuple> results = countryRepository.findIdAndShortCodeUsingTuple();
        for (Tuple tuple : results)
        {
//            LOGGER.info("Id: {} | Short Code: {}", tuple.get(0), tuple.get(1));
            LOGGER.info("Id: {} | Short Code: {}", tuple.get("id"), tuple.get("shortCode"));

            Integer id = (Integer) tuple.get("id");
            String shortCode = (String) tuple.get("shortCode");
        }
    }

    @Test
    public void findIdAndShortCodeUsingCustomProjectionTest()
    {
        List<CountryIdAndShortCodeProjection> countries = countryRepository.findIdAndShortCodeUsingCustomProjection();
        for (CountryIdAndShortCodeProjection projection : countries)
        {
            Integer id = projection.getId();
            String shortCode = projection.getShortCode();

            LOGGER.info("ID: {} | SHORT_CODE: {}", id, shortCode);
        }
    }

    @Test
    public void findIdAndShortCodeUsingNativeQueryCustomProjectionTest()
    {
        List<CountryIdAndShortCodeProjection> countries = countryRepository.findIdAndShortCodeUsingNativeQuery();
        for (CountryIdAndShortCodeProjection projection : countries)
        {
            Integer id = projection.getId();
            String shortCode = projection.getShortCode();

            LOGGER.info("ID: {} | SHORT_CODE: {}", id, shortCode);
        }
    }

    @Test
    public void findAllWithPageableTest()
    {
        Pageable pageable = PageRequest.of(0, 2);

        Page<Country> countryPage = countryRepository.findAll(pageable); // page = 0 (offset), size = 2 (limit), sort=null
        LOGGER.info("Country page: {}", countryPage);

        if (countryPage.hasContent())
        {
            List<Country> countries = countryPage.getContent();
            long totalElements = countryPage.getTotalElements();
            int totalPages = countryPage.getTotalPages();
        }
    }
}
