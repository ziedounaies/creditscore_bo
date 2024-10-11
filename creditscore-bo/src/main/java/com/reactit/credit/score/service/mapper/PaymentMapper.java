package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.Invoice;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.domain.Payment;
import com.reactit.credit.score.domain.Product;
import com.reactit.credit.score.service.dto.InvoiceDTO;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import com.reactit.credit.score.service.dto.PaymentDTO;
import com.reactit.credit.score.service.dto.ProductDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "memberUser", source = "memberUser", qualifiedByName = "memberUserId")
    @Mapping(target = "products", source = "products", qualifiedByName = "productIdSet")
    @Mapping(target = "invoice", source = "invoice", qualifiedByName = "invoiceId")
    PaymentDTO toDto(Payment s);

    @Mapping(target = "removeProduct", ignore = true)
    Payment toEntity(PaymentDTO paymentDTO);

    @Named("memberUserId")
    @Mapping(target = "id", source = "id")
    MemberUserDTO toDtoMemberUserId(MemberUser memberUser);

    @Named("productId")
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("productIdSet")
    default Set<ProductDTO> toDtoProductIdSet(Set<Product> product) {
        return product.stream().map(this::toDtoProductId).collect(Collectors.toSet());
    }

    @Named("invoiceId")
    @Mapping(target = "id", source = "id")
    InvoiceDTO toDtoInvoiceId(Invoice invoice);
}
