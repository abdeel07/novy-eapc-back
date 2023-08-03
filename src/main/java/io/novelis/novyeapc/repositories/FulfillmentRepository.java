package io.novelis.novyeapc.repositories;

import io.novelis.novyeapc.entities.Fulfillment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FulfillmentRepository extends JpaRepository<Fulfillment,Long> {
    List<Fulfillment> findByInterviewId(Long interview_id);
}
