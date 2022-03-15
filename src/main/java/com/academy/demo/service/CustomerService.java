package com.academy.demo.service;

import com.academy.demo.dto.CustomerWithAddressDTO;
import com.academy.demo.entity.Customer;
import com.academy.demo.mapper.CustomerMapper;
import com.academy.demo.repository.CustomerRepository;
import com.academy.demo.specs.CustomerSearchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    /**
     * Getting page of customer with addresses
     *
     * @param pageable page parameters
     * @return Page of CustomerWithAddressDTO Objects
     */
    public Page<CustomerWithAddressDTO> pageWithAddresses(Pageable pageable)
    {
        Page<Integer> customerIdsPage = customerRepository.findCustomerIds(pageable);
        List<Customer> customers = customerRepository.findAllByIdIn(customerIdsPage.getContent());

        List<CustomerWithAddressDTO> customerWithAddresses = customers.stream()
            .map(customerMapper::toAddressDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(
            customerWithAddresses, pageable, customerIdsPage.getTotalElements()
        );
    }

    public List<Customer> search(CustomerSearchSpecification customerSearchSpecification)
    {
        List<Customer> resultSet = customerRepository.findAll(customerSearchSpecification);
//        List<CustomerWithAddressDTO> customers = resultSet.stream()
//            .map(customerMapper::toAddressDTO)
//            .collect(Collectors.toList());

        return customerRepository.findAll(customerSearchSpecification); // SELECT * FROM customer WHERE ...
    }
}
