package org.sid.customerservice.services;

import org.sid.customerservice.dto.CustomerRequestDTO;
import org.sid.customerservice.dto.CustomerResponseDTO;
import org.sid.customerservice.entities.Customer;
import org.sid.customerservice.mappers.CustomerMapper;
import org.sid.customerservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper=customerMapper;
    }

    @Override
    public CustomerResponseDTO save(CustomerRequestDTO customerRequestDTO) {
        //generer un id: UUID.randomUUID().toString();
        Customer customer=customerMapper.CustomerRequestDTOToCustomer(customerRequestDTO);
        Customer savedcutomer=customerRepository.save(customer);
        return customerMapper.customerToCustomerResponseDTO(savedcutomer);
    }

    @Override
    public CustomerResponseDTO getCustomer(String id) {
        Customer customer=customerRepository.findById(id).get();
        return customerMapper.customerToCustomerResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO update(CustomerRequestDTO customerRequestDTO) {
        Customer customer=customerMapper.CustomerRequestDTOToCustomer(customerRequestDTO);
        Customer updatedCustomer=customerRepository.save(customer);
        return customerMapper.customerToCustomerResponseDTO(updatedCustomer);
    }

    @Override
    public List<CustomerResponseDTO> listCustomers() {
        List<Customer> customers=customerRepository.findAll();
        List<CustomerResponseDTO> customerResponseDTOS=customers.stream()
                .map(
                        customer ->
                                customerMapper.customerToCustomerResponseDTO(customer))
                .collect(Collectors.toList());
        return customerResponseDTOS;
    }
}
