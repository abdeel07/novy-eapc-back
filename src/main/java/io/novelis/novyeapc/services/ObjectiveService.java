package io.novelis.novyeapc.services;

import io.novelis.novyeapc.entities.enums.InterviewType;
import io.novelis.novyeapc.models.requests.ObjectiveRequest;
import io.novelis.novyeapc.models.responses.ObjectiveResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ObjectiveService {

    Map<String, Object> getAll(Pageable pageable);

    ObjectiveResponse add(ObjectiveRequest objectiveRequest);

    ObjectiveResponse get(Long id);

    ObjectiveResponse update(Long id, ObjectiveRequest objectiveRequest);

    void delete(Long id);

    Map<String, Object> searchByCollaboratorId(Long collaboratorId, Pageable pageable);

    Map<String, Object> searchByInterviewType(InterviewType interviewType, Pageable pageable);

    Map<String, Object> searchByCollaboratorIdAndInterviewType(Long collaboratorId, InterviewType interviewType, Pageable pageable);

    Map<String, Object> searchByYear(int year, Pageable pageable);

    Map<String, Object> searchByStartDateAndEndDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
