package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.Banks;
import com.reactit.credit.score.service.dto.BanksDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Banks} and its DTO {@link BanksDTO}.
 */
@Mapper(componentModel = "spring")
public interface BanksMapper extends EntityMapper<BanksDTO, Banks> {}
