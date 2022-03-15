package com.academy.demo;

import com.academy.demo.entity.Address;
import com.academy.demo.entity.City;
import com.academy.demo.entity.Country;
import com.academy.demo.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DemoAcademyApplication.class)
public class AddressIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;


    @Test
    public void findByPostalCodeTest()
    {
        List<Address> addresses = addressRepository.findByPostalCode("81000");
        assertThat(addresses).isNotNull();
        assertThat(addresses).isNotEmpty();
    }

    @Test
    public void findByCityNameTest()
    {
        List<Address> addresses = addressRepository.findByCityName("Peking");
        assertThat(addresses).isNotNull();
        assertThat(addresses).isNotEmpty();
    }

    @Test
    public void findByCityCountryShortCodeTest()
    {
        List<Address> addresses = addressRepository.findByCityCountryShortCode("ME");
        assertThat(addresses).isNotNull();
        assertThat(addresses).isNotEmpty();
    }

    @Test
    public void findByPostalCodeUsingJPQLTest()
    {
        List<Address> addresses = addressRepository.findByPostalCodeJpql("81000");
        assertThat(addresses).isNotNull();
        assertThat(addresses).isNotEmpty();
    }

    @Test
    public void findByCityNameUsingJPQLTest()
    {
        List<Address> addresses = addressRepository.findByCityNameJpql("Peking");
        assertThat(addresses).isNotNull();
        assertThat(addresses).isNotEmpty();
    }

    @Test
    public void findByCountryShortCodeUsingJPQLTest()
    {
        List<Address> addresses = addressRepository.findByCountryShortCodeJpql("ME");
        assertThat(addresses).isNotNull();
        assertThat(addresses).isNotEmpty();

        // no additional queries...
        City city = addresses.get(0).getCity();
        Country country = city.getCountry();

        assertThat(city).isNotNull();
        assertThat(country).isNotNull();
    }

    @Test
    public void findByUniqueIdentifierTest()
    {
        Address address = addressRepository.findByIdWithCity(1);
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isNotNull();
    }
}
