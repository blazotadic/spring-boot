package com.academy.demo;

import com.academy.demo.dto.CustomerWithAddressDTO;
import com.academy.demo.entity.Customer;
import com.academy.demo.mapper.CustomerMapper;
import com.academy.demo.projection.CustomerProjection;
import com.academy.demo.projection.CustomerWithAddressProjection;
import com.academy.demo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DemoAcademyApplication.class)
public class CustomerIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerIntegrationTest.class);

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    public void findByPhoneStartingWithTest()
    {
        String phoneStartingPart = "38269";
        Pageable pageable = PageRequest.of(0, 5);

        Page<Customer> customerPage = customerRepository.findByPhoneStartingWith(phoneStartingPart, pageable);
        assertThat(customerPage).isNotNull();
        assertThat(customerPage.hasContent()).isTrue();
        assertThat(customerPage.getContent().size()).isEqualTo(5);

        List<Customer> customers = customerPage.getContent();
        customers.forEach(customer -> LOGGER.info("Customer: {}", customer));
    }

    @Test
    public void findWithAddressesTest()
    {
        Pageable pageable = PageRequest.of(0, 3);

        Page<Integer> customersIds = customerRepository.findCustomerIds(pageable);
        assertThat(customersIds).isNotNull().isNotEmpty();

        List<Customer> customers = customerRepository.findAllByIdIn(customersIds.getContent());
        assertThat(customers).isNotNull().isNotEmpty();

        List<CustomerWithAddressDTO> customersWithAddresses = customers
            .stream()
            .map(customerMapper::toAddressDTO) // new CustomerWithAddressDTO(customer)
            .collect(Collectors.toList());

        customersWithAddresses.forEach(customer -> LOGGER.info("Customer: {}", customer));
    }

    @Test
    public void findByCityNameWithAddressProjectionTest()
    {
        String cityName = "Peking";
        List<CustomerWithAddressProjection> customers = customerRepository.findByCityNameWithAddressProjection(cityName);
        assertThat(customers).isNotNull();
        assertThat(customers).isNotEmpty();

        customers.forEach(customer -> LOGGER.info(
                "FULL NAME: {} | EMAIL: {} | CITY-NAME: {} | COUNTRY-NAME: {} | ADDRESS-STREET: {}",
                customer.getFullName(), customer.getEmail(), customer.getCityName(),
                customer.getCountryName(), customer.getAddressStreet()
            )
        );
    }

    @Test
    public void findByAddressesCityCountryNameTest()
    {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Customer> customerPage = customerRepository.findByAddressesCityCountryName(pageable);
        assertThat(customerPage).isNotNull();
        assertThat(customerPage.hasContent()).isTrue();
        assertThat(customerPage.getContent().size()).isEqualTo(5);

        List<Customer> customers = customerPage.getContent();
        customers.forEach(customer -> LOGGER.info("Customer: {}", customer));
    }

    @Test
    public void findByAddressesCityCountryNameUsingNativeTest()
    {
        Pageable pageable = PageRequest.of(0, 5);
        Page<CustomerWithAddressProjection> customerPage = customerRepository.findByAddressesCityCountryNameUsingNative(pageable);
        assertThat(customerPage).isNotNull();
        assertThat(customerPage.hasContent()).isTrue();
        assertThat(customerPage.getContent().size()).isEqualTo(5);

        List<CustomerWithAddressProjection> customers = customerPage.getContent();

        customers.forEach(customer -> LOGGER.info(
            "FULL NAME: {} | EMAIL: {} | CITY-NAME: {} | COUNTRY-NAME: {} | ADDRESS-STREET: {}",
            customer.getFullName(), customer.getEmail(), customer.getCityName(),
            customer.getCountryName(), customer.getAddressStreet()
            )
        );
    }

    @Test
    public void findIdByPhoneTest()
    {
        String phoneNumber = "38269657962";
        Integer customerId = customerRepository.findIdByPhone(phoneNumber);
        assertThat(customerId).isNotNull();
        assertThat(customerId).isEqualTo(1);

        LOGGER.info("Customer id: {}", customerId);
    }

    @Test
    public void findAllCustomersWithUpperFullNameTest()
    {
        List<String> customerFullNames = customerRepository.findAllCustomersWithUpperName();
        customerFullNames.forEach(fullName -> LOGGER.info("Full name: {}", fullName));
    }

    @Test
    @Transactional
    public void findAllCustomersUsingStoredProcedureTest()
    {
        List<CustomerProjection> customerProjections = customerRepository.findAllCustomersUsingStoredProcedure();
        customerProjections.forEach(
            customer -> LOGGER.info(
                "Id: {} | FULL_NAME: {} | EMAIL: {} | PHONE: {}",
                customer.getId(), customer.getFullName(), customer.getEmail(), customer.getPhone()
            )
        );
    }

    @Test
    @Transactional
    public void findAllCustomersByEmailDomainUsingStoredProcedureTest()
    {
        String emailPart = "yahoo";

        List<CustomerProjection> customerProjections = customerRepository
            .findAllCustomersByEmailPartUsingStoredProcedure(emailPart);

        customerProjections.forEach(
            customer -> LOGGER.info(
                "Id: {} | FULL_NAME: {} | EMAIL: {} | PHONE: {}",
                customer.getId(), customer.getFullName(), customer.getEmail(), customer.getPhone()
            )
        );
    }

    @Test
    @Transactional
    public void findCustomerIdByPhoneNumberUsingStoredProcedure()
    {
        String phoneNumber = "38269000000";
        Integer customerId = customerRepository.findIdByPhoneNumberUsingStoredProcedure(phoneNumber);

        LOGGER.info("Customer id: {}", customerId);
    }
}
