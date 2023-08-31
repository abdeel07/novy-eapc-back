package io.novelis.novyeapc.services.impl;

import io.novelis.novyeapc.entities.*;
import io.novelis.novyeapc.entities.enums.InterviewType;
import io.novelis.novyeapc.mappers.InterviewMapper;
import io.novelis.novyeapc.models.requests.FulfillmentRequest;
import io.novelis.novyeapc.models.requests.InterviewRequest;
import io.novelis.novyeapc.models.requests.QuizRequest;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import io.novelis.novyeapc.repositories.*;
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

    @Autowired
    ObjectiveRepository objectiveRepository;

    @Autowired
    NotificationRepository notificationRepository;
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

        if (interviewRequest.getObjectivesId() != null) {
            for (Long objectiveId : interviewRequest.getObjectivesId()) {
                Objective objective = objectiveRepository.findById(objectiveId).get();
                objective.setInterview(savedInterview);
                objectiveRepository.save(objective);


            }
        }
        Notification notification=new Notification();
        notification.setCollaborator(collaborator);
        notification.setDate(new Date());
        if(interviewRequest.getType().equals("Performance")){
            notification.setMessage("vous avez des questions à répondre sur l'entretien de performance");
        }else {
            notification.setMessage("vous avez des questions à répondre sur l'entretien d'évaluation");
        }
        notificationRepository.save(notification);
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
        Optional<Interview> interviewExist = interviewRepository.findById(id);

        if (!interviewExist.isPresent())
            throw new ResourceNotFoundException(id + "doesn't exist !");
        Interview interview = InterviewMapper.INSTANCE.interviewRequestToInterview(interviewRequest);
        Collaborator collaborator = collaboratorRepository.findById(interviewRequest.getCollaboratorId()).get();
        interview.setCollaborator(collaborator);
        interview.setId(id);
        Interview updatedInterview = interviewRepository.save(interview);

        if (interviewRequest.getFulfillments() != null) {
            for (FulfillmentRequest fulfillmentRequest : interviewRequest.getFulfillments()) {
                Long fulfillmentId = fulfillmentRequest.getId();

                if (fulfillmentId != null) {
                    Fulfillment fulfillment = fulfillmentRepository.findById(fulfillmentId).orElse(null);

                    if (fulfillment != null) {
                        fulfillment.setTitle(fulfillmentRequest.getTitle());
                        fulfillmentRepository.save(fulfillment);
                    }
                } else {
                    Fulfillment fulfillment = new Fulfillment();
                    fulfillment.setInterview(updatedInterview);
                    fulfillment.setComment(fulfillmentRequest.getComment());
                    fulfillment.setTitle(fulfillmentRequest.getTitle());
                    fulfillmentRepository.save(fulfillment);
                }
            }
        }

        if (interviewRequest.getQuizzes() != null) {
            for (QuizRequest quizRequest : interviewRequest.getQuizzes()) {
                Long quizId=quizRequest.getId();
                if (quizId != null) {
                    Quiz quiz = quizRepository.findById(quizId).orElse(null);

                    if (quiz != null) {
                        quiz.setQuestion(quizRequest.getQuestion());
                        quizRepository.save(quiz);
                    }
                } else {
                    Quiz quiz = new Quiz();
                    quiz.setInterview(updatedInterview);
                    quiz.setAnswer(quizRequest.getAnswer());
                    quiz.setQuestion(quizRequest.getQuestion());
                    quizRepository.save(quiz);
                }

            }
        }



        return InterviewMapper.INSTANCE.interviewToInterviewResponse
                (updatedInterview);

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
    public Map<String, Object> searchByIdAndDate(Long id, int year, Pageable pageable) {
        List<InterviewResponse> responses = new ArrayList<>();

        Page<Interview> interviews = interviewRepository.findByIdAndYear(id,year,pageable);

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

    @Override
    public Map<String, Object> searchByAllAttributes(String name, int year, Set<InterviewType> interviewTypes, Set<Long> collaboratorsId, Pageable pageable) {
        List<InterviewResponse> responses = new ArrayList<>();
        if(collaboratorsId.isEmpty()){
            List<Collaborator> collaborators=collaboratorRepository.findAll();
            for (Collaborator collaborator:collaborators
            ) {
                collaboratorsId.add(collaborator.getId());
            }
        }

        Page<Interview> interviews = interviewRepository.findByAllAttributes(name, year, interviewTypes,collaboratorsId, pageable);

        responses = InterviewMapper.INSTANCE.mapInterview(interviews.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", interviews.getNumber());
        page.put("totalElements", interviews.getTotalElements());
        page.put("totalPages", interviews.getTotalPages());

        return page;
    }
}
