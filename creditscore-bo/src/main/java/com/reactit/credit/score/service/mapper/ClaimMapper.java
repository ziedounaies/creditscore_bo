package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.Claim;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.service.dto.ClaimDTO;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Claim} and its DTO {@link ClaimDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClaimMapper extends EntityMapper<ClaimDTO, Claim> {
    @Mapping(target = "memberUser", source = "memberUser", qualifiedByName = "memberUserId")
    ClaimDTO toDto(Claim s);

    @Named("memberUserId")
    @Mapping(target = "id", source = "id")
    MemberUserDTO toDtoMemberUserId(MemberUser memberUser);
}
