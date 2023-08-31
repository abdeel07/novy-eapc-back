package io.novelis.novyeapc.services;

import io.novelis.novyeapc.entities.enums.InterviewType;
import io.novelis.novyeapc.models.requests.ObjectiveRequest;
import io.novelis.novyeapc.models.responses.ObjectiveResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ObjectiveService {

    Map<String, Object> getAll(Pageable pageable);

    ObjectiveResponse add(ObjectiveRequest objectiveRequest);

    ObjectiveResponse get(Long id);

    ObjectiveResponse update(Long id, ObjectiveRequest objectiveRequest) throws Exception;

    void delete(Long id);

    Map<String, Object> searchByCollaboratorId(Long collaboratorId,int year, Pageable pageable);

    Map<String, Object> searchByCollaboratorName(String name, int year, Pageable pageable);

    Map<String, Object> searchByInterviewType(InterviewType interviewType, Pageable pageable);

    Map<String, Object> searchByCollaboratorIdAndInterviewTypeAndYear(Long collaboratorId, InterviewType interviewType,int year, Pageable pageable);

    Map<String, Object> searchByYear(int year, Pageable pageable);

    Map<String, Object> searchByStartDateAndEndDateBetween(Date startDate, Date endDate, Pageable pageable);

    Map<String, Object> searchByAllAttributes(String name,
                                              int year,
                                              Set<InterviewType> interviewTypes,
                                              Set<String> status,
                                              Set<Long> collaboratorsId,
                                              Pageable pageable);
}
