package io.novelis.novyeapc.services;

import io.novelis.novyeapc.entities.enums.InterviewType;
import io.novelis.novyeapc.models.requests.InterviewRequest;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
public interface InterviewService {

    Map<String, Object> getAll(Pageable pageable);

    InterviewResponse add(InterviewRequest interviewRequest);

    InterviewResponse get(Long id);

    InterviewResponse update(Long id, InterviewRequest interviewRequest);

    void delete(Long id);

    Map<String, Object> searchByDate(int year, Pageable pageable);
    Map<String, Object>   searchByIdAndDate(Long id, int year,Pageable pageable);
    Map<String, Object> searchByType(InterviewType type, Pageable pageable);

    Map<String, Object> searchByAllAttributes(String name, int year, Set<InterviewType> interviewTypes, Set<Long> collaboratorsId, Pageable pageable);
}
