package com.academy.demo.controller;

import com.academy.demo.dto.CustomerWithAddressDTO;
import com.academy.demo.entity.Customer;
import com.academy.demo.search.CustomerSearch;
import com.academy.demo.service.CustomerService;
import com.academy.demo.specs.CustomerSearchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/with-addresses")
    // @PreAuthorize("@customAuth.hasPermission()")
    @PreAuthorize("@customAuth.hasPermissionBasedOnSomething(#authKey)")
    // @Secured("MANAGER") <=> @PreAuthorize("hasRole('ADMIN')") <=> security hasRole()
    public ResponseEntity<Page<CustomerWithAddressDTO>> pageWithAddresses(
            Pageable pageable, @RequestHeader(value = "Authorization") String authKey)
    {
        Page<CustomerWithAddressDTO> customerWithAddressesPage = customerService.pageWithAddresses(pageable);
        return new ResponseEntity<>(customerWithAddressesPage, HttpStatus.OK);
    }

    @GetMapping(value = "search")
    public ResponseEntity<List<Customer>> search(CustomerSearch customerSearch)
    {
        CustomerSearchSpecification customerSearchSpecification = new CustomerSearchSpecification(customerSearch);
        List<Customer> customers = customerService.search(customerSearchSpecification);

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
