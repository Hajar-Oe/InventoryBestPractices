package org.sid.billingservice.services;

import org.sid.billingservice.dto.InvoiceRequestDTO;
import org.sid.billingservice.dto.InvoiceResponseDTO;
import org.sid.billingservice.entities.Invoice;
import org.sid.billingservice.exceptions.CustomerNotFoundException;
import org.sid.billingservice.mappers.InvoiceMapper;
import org.sid.billingservice.modele.Customer;
import org.sid.billingservice.repositories.InvoiceRepository;
import org.sid.billingservice.restclients.CustomerRestClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private InvoiceMapper  invoiceMapper;
    private CustomerRestClient customerRestClient;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CustomerRestClient customerRestClient) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerRestClient = customerRestClient;
    }

    @Override
    public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) {
        Customer customer=null;
        try{
            customer =customerRestClient.getCustomer(invoiceRequestDTO.getCustomerId());
        } catch (Exception e){
            throw new CustomerNotFoundException("Customer Not Found");
        }

        Invoice invoice=invoiceMapper.fromInvoiceRequestDTO(invoiceRequestDTO);

        invoice.setId(UUID.randomUUID().toString());
        invoice.setDate(new Date());
        //Vérification de l'intégrité réferentielle: si le client existe
        Invoice savedInvoice=invoiceRepository.save(invoice);
        savedInvoice.setCustomer(customer);

        return invoiceMapper.fromInvoice(savedInvoice);
    }

    @Override
    public InvoiceResponseDTO getInvoice(String invoiceId) {

        Invoice invoice=invoiceRepository.findById(invoiceId).get();
        Customer customer=customerRestClient.getCustomer(invoice.getCustomerId());
        invoice.setCustomer(customer);

        return invoiceMapper.fromInvoice(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> invoicesByCustomerId(String customerId) {
        List<Invoice> invoices=invoiceRepository.findByCustomerId(customerId);

        for(Invoice invoice:invoices){
            Customer customer=customerRestClient.getCustomer(invoice.getCustomerId());
            invoice.setCustomer(customer);
        }

        List<InvoiceResponseDTO> customerResponseDTOS=invoices.stream()
                .map(
                        invoice ->
                                invoiceMapper.fromInvoice(invoice))
                .collect(Collectors.toList());


        return customerResponseDTOS;
    }

    @Override
    public List<InvoiceResponseDTO> allInvoices() {
        List<Invoice> invoices=invoiceRepository.findAll();
       for(Invoice invoice:invoices){
           Customer customer=customerRestClient.getCustomer(invoice.getCustomerId());
           invoice.setCustomer(customer);
       }
        List<InvoiceResponseDTO> customerResponseDTOS=invoices.stream()
                .map(
                        invoice ->
                                invoiceMapper.fromInvoice(invoice))
                .collect(Collectors.toList());


        return customerResponseDTOS;
    }
}
