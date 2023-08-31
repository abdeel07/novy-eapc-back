package io.novelis.novyeapc.repositories;

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
public interface ObjectiveRepository extends JpaRepository<Objective,Long> {
    @Query("SELECT o FROM Objective o WHERE o.collaborator.id = :collaboratorId AND YEAR(o.startDate) =:year")
    Page<Objective> findByCollaboratorId(Long collaboratorId,int year, Pageable pageable);

    @Query("SELECT o FROM Objective o WHERE YEAR(o.startDate) = :year AND (o.collaborator.lastName LIKE %:name% OR o.collaborator.firstName LIKE %:name%)")
    Page<Objective> findByCollaboratorNameContainingIgnoreCaseAndYear(String name, int year, Pageable pageable);

    Page<Objective> findByInterviewType(InterviewType interviewType, Pageable pageable);

    @Query("SELECT o FROM Objective o WHERE o.collaborator.id = :collaboratorId AND o.interviewType = :interviewType AND YEAR(o.endDate) = :year AND o.interview.id IS NULL")
    Page<Objective> findByCollaboratorIdAndInterviewTypeAndYear(Long collaboratorId, InterviewType interviewType, int year, Pageable pageable);

    @Query("SELECT o FROM Objective o WHERE o.startDate BETWEEN :startDate AND :endDate AND o.endDate BETWEEN :startDate AND :endDate")
    Page<Objective> findByStartDateAndEndDateBetween(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT o FROM Objective o WHERE YEAR(o.startDate) = ?1 OR YEAR(o.endDate) = ?1")
    Page<Objective> findByDate(int year, Pageable pageable);

    @Query("SELECT o FROM Objective o WHERE YEAR(o.startDate) = :year " +
            "AND (o.collaborator.lastName LIKE %:name% OR o.collaborator.firstName LIKE %:name%) " +
            "AND ( o.interviewType IN :interviewTypes) " +
            "AND ( o.status IN :status) " +
            "AND (o.collaborator.id IN :collaboratorsId  )")
    Page<Objective> findByAllAttributes(String name,
                                        int year,
                                        Set<InterviewType> interviewTypes,
                                        Set<String> status,
                                        Set<Long> collaboratorsId,
                                        Pageable pageable);
}
