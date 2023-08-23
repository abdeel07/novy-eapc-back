package io.novelis.novyeapc.repositories;

import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.entities.enums.InterviewType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InterviewRepository extends JpaRepository<Interview,Long> {
    Page<Interview> findByType(InterviewType type, Pageable pageable);

//    Page<Interview> findByDate(Date date, Pageable pageable);

    @Query("SELECT i FROM Interview i WHERE YEAR(i.date) = ?1")
    Page<Interview> findByDate(int year, Pageable pageable);

    @Query("SELECT i FROM Interview i WHERE i.collaborator.id = ?1 AND YEAR(i.date) = ?2")
    Page<Interview> findByIdAndYear(Long id, int year, Pageable pageable);

}
