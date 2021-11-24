package org.sid.billingservice.restclients;

import org.sid.billingservice.modele.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {

    @GetMapping(path = "/api/customers/{id}")
    Customer getCustomer(@PathVariable(name = "id") String id);


    @GetMapping(path = "/api/customers")
    List<Customer> allCustomers();
}
