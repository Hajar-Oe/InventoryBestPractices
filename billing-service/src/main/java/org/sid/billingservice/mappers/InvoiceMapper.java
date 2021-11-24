package org.sid.billingservice.mappers;

import org.mapstruct.Mapper;
import org.sid.billingservice.dto.InvoiceRequestDTO;
import org.sid.billingservice.dto.InvoiceResponseDTO;
import org.sid.billingservice.entities.Invoice;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice fromInvoiceRequestDTO(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO fromInvoice(Invoice invoice);
}
