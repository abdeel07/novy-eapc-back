package io.novelis.novyeapc.services.impl;

import io.novelis.novyeapc.entities.Collaborator;
import io.novelis.novyeapc.entities.Fulfillment;
import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.entities.Quiz;
import io.novelis.novyeapc.entities.enums.InterviewType;
import io.novelis.novyeapc.mappers.InterviewMapper;
import io.novelis.novyeapc.models.requests.FulfillmentRequest;
import io.novelis.novyeapc.models.requests.InterviewRequest;
import io.novelis.novyeapc.models.requests.QuizRequest;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import io.novelis.novyeapc.repositories.CollaboratorRepository;
import io.novelis.novyeapc.repositories.FulfillmentRepository;
import io.novelis.novyeapc.repositories.InterviewRepository;
import io.novelis.novyeapc.repositories.QuizRepository;
import io.novelis.novyeapc.services.CollaboratorService;
import io.novelis.novyeapc.services.InterviewService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Autowired
    FulfillmentRepository fulfillmentRepository;
    @Autowired
    QuizRepository quizRepository;

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
    @Transactional
    public InterviewResponse add(InterviewRequest interviewRequest) {

        Interview interview = InterviewMapper.INSTANCE.interviewRequestToInterview(interviewRequest);
        Collaborator collaborator = collaboratorRepository.findById(interviewRequest.getCollaboratorId()).get();
        interview.setCollaborator(collaborator);
        System.out.println(collaborator.getId());
        System.out.println(interview.getFulfillments());
        Interview savedInterview = interviewRepository.save(interview);

        if (interviewRequest.getFulfillments() != null) {
            for (FulfillmentRequest fulfillmentRequest : interviewRequest.getFulfillments()) {
                Fulfillment fulfillment = new Fulfillment();
                fulfillment.setInterview(savedInterview);
                fulfillment.setComment(fulfillmentRequest.getComment());
                fulfillment.setTitle(fulfillmentRequest.getTitle());
                fulfillmentRepository.save(fulfillment);

            }
        }
        if (interviewRequest.getQuizzes() != null) {
            for (QuizRequest quizRequest : interviewRequest.getQuizzes()) {
                Quiz quiz = new Quiz();
                quiz.setInterview(savedInterview);
                quiz.setAnswer(quizRequest.getAnswer());
                quiz.setQuestion(quizRequest.getQuestion());
                quizRepository.save(quiz);

            }
        }
        return InterviewMapper.INSTANCE.interviewToInterviewResponse
                (savedInterview);
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
