package io.novelis.novyeapc.services.impl;

import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.entities.enums.InterviewType;
import io.novelis.novyeapc.mappers.InterviewMapper;
import io.novelis.novyeapc.models.requests.InterviewRequest;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import io.novelis.novyeapc.repositories.InterviewRepository;
import io.novelis.novyeapc.services.InterviewService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    InterviewRepository interviewRepository;

    @Override
    public Map<String, Object> getAll(Pageable pageable) {
        List<InterviewResponse> responses = new ArrayList<>();

        Page<Interview> interviews = interviewRepository.findAll(pageable);

        responses = InterviewMapper.INSTANCE.mapInterview(interviews.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", interviews.getNumber());
        page.put("totalElements", interviews.getTotalElements());
        page.put("totalPages", interviews.getTotalPages());

        return page;
    }

    @Override
    public InterviewResponse add(InterviewRequest interviewRequest) {

        Interview interview = InterviewMapper.INSTANCE.interviewRequestToInterview(interviewRequest);

        return InterviewMapper.INSTANCE.interviewToInterviewResponse
                (interviewRepository.save(interview));
    }

    @Override
    public InterviewResponse get(Long id) {

        Optional<Interview> interview = interviewRepository.findById(id);

        return InterviewMapper.INSTANCE.interviewToInterviewResponse
                (interview.get());
    }

    @Override
    public InterviewResponse update(Long id, InterviewRequest interviewRequest) {
        Optional<Interview> interview = interviewRepository.findById(id);

        if (!interview.isPresent())
            throw new ResourceNotFoundException(id + "doesn't exist !");

        interview.get().setType(interviewRequest.getType());
        interview.get().setDate(interviewRequest.getDate());

        return InterviewMapper.INSTANCE.interviewToInterviewResponse
                (interviewRepository.save(interview.get()));
    }

    @Override
    public void delete(Long id) {
        Optional<Interview> interview = interviewRepository.findById(id);

        if (!interview.isPresent())
            throw new ResourceNotFoundException(id + "doesn't exist !");

        interviewRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> searchByDate(int year, Pageable pageable) {
        List<InterviewResponse> responses = new ArrayList<>();

        Page<Interview> interviews = interviewRepository.findByDate(year, pageable);

        responses = InterviewMapper.INSTANCE.mapInterview(interviews.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", interviews.getNumber());
        page.put("totalElements", interviews.getTotalElements());
        page.put("totalPages", interviews.getTotalPages());

        return page;
    }

    @Override
    public Map<String, Object> searchByType(InterviewType type, Pageable pageable) {
        List<InterviewResponse> responses = new ArrayList<>();

        Page<Interview> interviews = interviewRepository.findByType(type, pageable);

        responses = InterviewMapper.INSTANCE.mapInterview(interviews.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", interviews.getNumber());
        page.put("totalElements", interviews.getTotalElements());
        page.put("totalPages", interviews.getTotalPages());

        return page;
    }
}
