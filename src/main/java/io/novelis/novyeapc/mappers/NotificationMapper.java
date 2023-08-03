package io.novelis.novyeapc.mappers;

import io.novelis.novyeapc.entities.Notification;
import io.novelis.novyeapc.models.requests.NotificationRequest;
import io.novelis.novyeapc.models.responses.NotificationResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    Notification notificationRequestToNotification(NotificationRequest notificationRequest);

    NotificationResponse notificationToNotificationResponse(Notification notification);

    List<NotificationResponse> mapNotification(List<Notification> notifications);
}
