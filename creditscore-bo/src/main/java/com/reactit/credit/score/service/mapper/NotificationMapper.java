package com.reactit.credit.score.service.mapper;

import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.domain.Notification;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import com.reactit.credit.score.service.dto.NotificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "memberUser", source = "memberUser", qualifiedByName = "memberUserId")
    NotificationDTO toDto(Notification s);

    @Named("memberUserId")
    @Mapping(target = "id", source = "id")
    MemberUserDTO toDtoMemberUserId(MemberUser memberUser);
}
