package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.CreditRapport;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.service.dto.CreditRapportDTO;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditRapport} and its DTO {@link CreditRapportDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreditRapportMapper extends EntityMapper<CreditRapportDTO, CreditRapport> {
    @Mapping(target = "memberUser", source = "memberUser", qualifiedByName = "memberUserId")
    CreditRapportDTO toDto(CreditRapport s);

    @Named("memberUserId")
    @Mapping(target = "id", source = "id")
    MemberUserDTO toDtoMemberUserId(MemberUser memberUser);
}
