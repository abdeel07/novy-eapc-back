package io.novelis.novyeapc.repositories;

import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.entities.Objective;
import io.novelis.novyeapc.entities.enums.InterviewType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface InterviewRepository extends JpaRepository<Interview,Long> {
    Page<Interview> findByType(InterviewType type, Pageable pageable);

//    Page<Interview> findByDate(Date date, Pageable pageable);

    @Query("SELECT i FROM Interview i WHERE YEAR(i.date) = ?1")
    Page<Interview> findByDate(int year, Pageable pageable);

    @Query("SELECT i FROM Interview i WHERE i.collaborator.id = ?1 AND YEAR(i.date) = ?2")
    Page<Interview> findByIdAndYear(Long id, int year, Pageable pageable);

    @Query("SELECT o FROM Interview o WHERE YEAR(o.date) = :year " +
            "AND (o.collaborator.lastName LIKE %:name% OR o.collaborator.firstName LIKE %:name%) " +
            "AND ( o.type IN :interviewTypes) " +
            "AND (o.collaborator.id IN :collaboratorsId  )")
    Page<Interview> findByAllAttributes(String name,
                                        int year,
                                        Set<InterviewType> interviewTypes,
                                        Set<Long> collaboratorsId,
                                        Pageable pageable);
}


