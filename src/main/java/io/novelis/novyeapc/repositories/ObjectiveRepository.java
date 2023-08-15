package io.novelis.novyeapc.repositories;

import io.novelis.novyeapc.entities.Objective;
import io.novelis.novyeapc.entities.enums.InterviewType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective,Long> {

    Page<Objective> findByCollaboratorId(Long collaboratorId, Pageable pageable);

    Page<Objective> findByInterviewType(InterviewType interviewType, Pageable pageable);

    @Query("SELECT o FROM Objective o WHERE o.collaborator.id = :collaboratorId AND o.interviewType = :interviewType AND YEAR(o.endDate) = :year")
    Page<Objective> findByCollaboratorIdAndInterviewTypeAndYear(Long collaboratorId, InterviewType interviewType,int year, Pageable pageable);

    @Query("SELECT o FROM Objective o WHERE o.startDate BETWEEN :startDate AND :endDate AND o.endDate BETWEEN :startDate AND :endDate")
    Page<Objective> findByStartDateAndEndDateBetween(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT o FROM Objective o WHERE YEAR(o.startDate) = ?1 OR YEAR(o.endDate) = ?1")
    Page<Objective> findByDate(int year, Pageable pageable);
}
