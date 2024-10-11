package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.Address;
import com.reactit.credit.score.domain.Agencies;
import com.reactit.credit.score.domain.Banks;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.service.dto.AddressDTO;
import com.reactit.credit.score.service.dto.AgenciesDTO;
import com.reactit.credit.score.service.dto.BanksDTO;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "memberUser", source = "memberUser", qualifiedByName = "memberUserId")
    @Mapping(target = "banks", source = "banks", qualifiedByName = "banksId")
    @Mapping(target = "agencies", source = "agencies", qualifiedByName = "agenciesId")
    AddressDTO toDto(Address s);

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
