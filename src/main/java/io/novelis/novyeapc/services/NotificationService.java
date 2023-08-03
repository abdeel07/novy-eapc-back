package io.novelis.novyeapc.services;

import io.novelis.novyeapc.models.requests.NotificationRequest;
import io.novelis.novyeapc.models.responses.NotificationResponse;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface NotificationService {

//    List<NotificationResponse> getAll();

    NotificationResponse add(NotificationRequest notificationRequest);

//    NotificationResponse get(Long id);

    Map<String, Object> searchByCollaboratorId(Long collaboratorId, Pageable pageable);
}
