package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.Agencies;
import com.reactit.credit.score.domain.Banks;
import com.reactit.credit.score.service.dto.AgenciesDTO;
import com.reactit.credit.score.service.dto.BanksDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agencies} and its DTO {@link AgenciesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgenciesMapper extends EntityMapper<AgenciesDTO, Agencies> {
    @Mapping(target = "banks", source = "banks", qualifiedByName = "banksId")
    AgenciesDTO toDto(Agencies s);

    @Named("banksId")
    @Mapping(target = "id", source = "id")
    BanksDTO toDtoBanksId(Banks banks);
}
