package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MemberUser} and its DTO {@link MemberUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberUserMapper extends EntityMapper<MemberUserDTO, MemberUser> {}
