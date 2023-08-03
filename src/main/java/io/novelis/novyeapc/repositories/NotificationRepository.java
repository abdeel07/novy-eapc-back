package io.novelis.novyeapc.repositories;

import io.novelis.novyeapc.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Page<Notification> findTop5ByCollaboratorIdOrderByDateDesc(Long collaboratorId, Pageable pageable);
}
