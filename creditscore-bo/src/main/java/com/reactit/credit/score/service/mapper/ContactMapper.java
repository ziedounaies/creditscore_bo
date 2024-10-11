package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.Agencies;
import com.reactit.credit.score.domain.Banks;
import com.reactit.credit.score.domain.Contact;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.service.dto.AgenciesDTO;
import com.reactit.credit.score.service.dto.BanksDTO;
import com.reactit.credit.score.service.dto.ContactDTO;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contact} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {
    @Mapping(target = "memberUser", source = "memberUser", qualifiedByName = "memberUserId")
    @Mapping(target = "banks", source = "banks", qualifiedByName = "banksId")
    @Mapping(target = "agencies", source = "agencies", qualifiedByName = "agenciesId")
    ContactDTO toDto(Contact s);

    @Named("memberUserId")
    @Mapping(target = "id", source = "id")
    MemberUserDTO toDtoMemberUserId(MemberUser memberUser);

    @Named("banksId")
    @Mapping(target = "id", source = "id")
    BanksDTO toDtoBanksId(Banks banks);

    @Named("agenciesId")
    @Mapping(target = "id", source = "id")
    AgenciesDTO toDtoAgenciesId(Agencies agencies);
}
