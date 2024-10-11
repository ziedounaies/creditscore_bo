package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.Invoice;
import com.reactit.credit.score.domain.Product;
import com.reactit.credit.score.service.dto.InvoiceDTO;
import com.reactit.credit.score.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "invoice", source = "invoice", qualifiedByName = "invoiceId")
    ProductDTO toDto(Product s);

    @Named("invoiceId")
    @Mapping(target = "id", source = "id")
    InvoiceDTO toDtoInvoiceId(Invoice invoice);
}
