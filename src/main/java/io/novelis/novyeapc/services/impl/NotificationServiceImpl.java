package io.novelis.novyeapc.services.impl;

import io.novelis.novyeapc.entities.Notification;
import io.novelis.novyeapc.mappers.NotificationMapper;
import io.novelis.novyeapc.models.requests.NotificationRequest;
import io.novelis.novyeapc.models.responses.NotificationResponse;
import io.novelis.novyeapc.repositories.NotificationRepository;
import io.novelis.novyeapc.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

//    @Override
//    public List<NotificationResponse> getAll() {
//        return NotificationMapper.INSTANCE.mapNotification(notificationRepository.findAll());
//    }

    @Override
    public NotificationResponse add(NotificationRequest notificationRequest) {
        Notification notification = NotificationMapper.INSTANCE.notificationRequestToNotification(notificationRequest);

        return NotificationMapper.INSTANCE.notificationToNotificationResponse(notification);
    }

//    @Override
//    public NotificationResponse get(Long id) {
//        Optional<Notification> notification = notificationRepository.findById(id);
//
//        if(!notification.isPresent())
//            return null;
//
//        return NotificationMapper.INSTANCE.notificationToNotificationResponse
//                (notification.get());
//    }

    @Override
    public Map<String, Object> searchByCollaboratorId(Long collaboratorId, Pageable pageable) {
        List<NotificationResponse> responses = new ArrayList<>();

        Page<Notification> notifications = notificationRepository.findTop5ByCollaboratorIdOrderByDateDesc(collaboratorId, pageable);

        responses = NotificationMapper.INSTANCE.mapNotification(notifications.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", notifications.getNumber());
        page.put("totalElements", notifications.getTotalElements());
        page.put("totalPages", notifications.getTotalPages());

        return page;
    }
}
