package org.sid.customerservice.mappers;

import org.mapstruct.Mapper;
import org.sid.customerservice.dto.CustomerRequestDTO;
import org.sid.customerservice.dto.CustomerResponseDTO;
import org.sid.customerservice.entities.Customer;

@Mapper(componentModel= "spring")
public interface CustomerMapper {
    CustomerResponseDTO customerToCustomerResponseDTO(Customer customer);
    Customer CustomerRequestDTOToCustomer(CustomerRequestDTO customerRequestDTO);
}
