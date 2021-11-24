package org.sid.billingservice.services;

import org.sid.billingservice.dto.InvoiceRequestDTO;
import org.sid.billingservice.dto.InvoiceResponseDTO;
import org.sid.billingservice.entities.Invoice;

import java.util.List;

public interface InvoiceService {
    InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO getInvoice(String invoiceId);
    List<InvoiceResponseDTO> invoicesByCustomerId(String customerId);
    List<InvoiceResponseDTO> allInvoices();
}
