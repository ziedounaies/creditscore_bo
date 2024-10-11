package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.CreditRapport;
import com.reactit.credit.score.domain.Invoice;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.service.dto.CreditRapportDTO;
import com.reactit.credit.score.service.dto.InvoiceDTO;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoice} and its DTO {@link InvoiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {
    @Mapping(target = "memberUser", source = "memberUser", qualifiedByName = "memberUserId")
    @Mapping(target = "creditRapport", source = "creditRapport", qualifiedByName = "creditRapportId")
    InvoiceDTO toDto(Invoice s);

    @Named("memberUserId")
    @Mapping(target = "id", source = "id")
    MemberUserDTO toDtoMemberUserId(MemberUser memberUser);

    @Named("creditRapportId")
    @Mapping(target = "id", source = "id")
    CreditRapportDTO toDtoCreditRapportId(CreditRapport creditRapport);
}
