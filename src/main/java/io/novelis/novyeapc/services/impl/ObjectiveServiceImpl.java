package io.novelis.novyeapc.services.impl;

import io.novelis.novyeapc.entities.Collaborator;
import io.novelis.novyeapc.entities.Objective;
import io.novelis.novyeapc.entities.enums.InterviewType;
import io.novelis.novyeapc.mappers.ObjectiveMapper;
import io.novelis.novyeapc.models.requests.ObjectiveRequest;
import io.novelis.novyeapc.models.responses.ObjectiveResponse;
import io.novelis.novyeapc.repositories.CollaboratorRepository;
import io.novelis.novyeapc.repositories.InterviewRepository;
import io.novelis.novyeapc.repositories.ObjectiveRepository;
import io.novelis.novyeapc.services.ObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ObjectiveServiceImpl implements ObjectiveService {

    @Autowired
    ObjectiveRepository objectiveRepository;

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Override
    public Map<String, Object> getAll(Pageable pageable) {
        List<ObjectiveResponse> responses = new ArrayList<>();
        Page<Objective> objectives = objectiveRepository.findAll(pageable);

        responses = ObjectiveMapper.INSTANCE.mapObjective(objectives.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", objectives.getNumber());
        page.put("totalElements", objectives.getTotalElements());
        page.put("totalPages", objectives.getTotalPages());

        return page;
    }

    @Override
    public ObjectiveResponse add(ObjectiveRequest objectiveRequest) {
        Objective objective = ObjectiveMapper.INSTANCE.objectiveRequestToObjective(objectiveRequest);

        Collaborator collaborator = collaboratorRepository.findById(objectiveRequest.getCollaboratorId()).get();
        objective.setCollaborator(collaborator);

        return ObjectiveMapper.INSTANCE.objectiveToObjectiveResponse
                (objectiveRepository.save(objective));
    }

    @Override
    public ObjectiveResponse get(Long id) {
        Objective objective = objectiveRepository.findById(id).get();

        if (objective == null)
            return null;

        return ObjectiveMapper.INSTANCE.objectiveToObjectiveResponse(objective);
    }

    @Override
    public ObjectiveResponse update(Long id, ObjectiveRequest objectiveRequest) throws Exception {
        Optional<Objective> findObjective = objectiveRepository.findById(id);

        if (!findObjective.isPresent())
            return null;

        Objective objective = ObjectiveMapper.INSTANCE.objectiveRequestToObjective(objectiveRequest);
        System.out.println(objective.getTitle());
        objective.setId(id);

        // Handle Collaborator entity if needed
        if (objectiveRequest.getCollaboratorId() != null) {
            Collaborator collaborator = collaboratorRepository.findById(objectiveRequest.getCollaboratorId())
                    .orElseThrow(() -> new Exception("Collaborator not found"));
            objective.setCollaborator(collaborator);
        }

        return ObjectiveMapper.INSTANCE.objectiveToObjectiveResponse
                (objectiveRepository.save(objective));
    }

    @Override
    public void delete(Long id) {
        Optional<Objective> findObjective = objectiveRepository.findById(id);

        if (!findObjective.isPresent()) {
            //throw new exception
        }

        objectiveRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> searchByCollaboratorId(Long collaboratorId,int year, Pageable pageable) {
        List<ObjectiveResponse> responses = new ArrayList<>();

        Page<Objective> objectives = objectiveRepository.findByCollaboratorId(collaboratorId, year, pageable);

        responses = ObjectiveMapper.INSTANCE.mapObjective(objectives.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", objectives.getNumber());
        page.put("totalElements", objectives.getTotalElements());
        page.put("totalPages", objectives.getTotalPages());

        return page;
    }

    @Override
    public Map<String, Object> searchByCollaboratorName(String name, int year, Pageable pageable) {
        List<ObjectiveResponse> responses = new ArrayList<>();

        Page<Objective> objectives = objectiveRepository.findByCollaboratorNameContainingIgnoreCaseAndYear(name, year, pageable);

        responses = ObjectiveMapper.INSTANCE.mapObjective(objectives.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", objectives.getNumber());
        page.put("totalElements", objectives.getTotalElements());
        page.put("totalPages", objectives.getTotalPages());

        return page;
    }

    @Override
    public Map<String, Object> searchByInterviewType(InterviewType interviewType, Pageable pageable) {
        List<ObjectiveResponse> responses = new ArrayList<>();

        Page<Objective> objectives = objectiveRepository.findByInterviewType(interviewType, pageable);

        responses = ObjectiveMapper.INSTANCE.mapObjective(objectives.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", objectives.getNumber());
        page.put("totalElements", objectives.getTotalElements());
        page.put("totalPages", objectives.getTotalPages());

        return page;
    }

    @Override
    public Map<String, Object> searchByCollaboratorIdAndInterviewTypeAndYear(Long collaboratorId, InterviewType interviewType,int year, Pageable pageable) {
        List<ObjectiveResponse> responses = new ArrayList<>();

        Page<Objective> objectives = objectiveRepository.findByCollaboratorIdAndInterviewTypeAndYear(collaboratorId, interviewType, year,pageable);

        responses = ObjectiveMapper.INSTANCE.mapObjective(objectives.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", objectives.getNumber());
        page.put("totalElements", objectives.getTotalElements());
        page.put("totalPages", objectives.getTotalPages());

        return page;
    }

    @Override
    public Map<String, Object> searchByYear(int year, Pageable pageable) {
        List<ObjectiveResponse> responses = new ArrayList<>();

        Page<Objective> objectives = objectiveRepository.findByDate(year, pageable);

        responses = ObjectiveMapper.INSTANCE.mapObjective(objectives.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", objectives.getNumber());
        page.put("totalElements", objectives.getTotalElements());
        page.put("totalPages", objectives.getTotalPages());

        return page;
    }

    @Override
    public Map<String, Object> searchByStartDateAndEndDateBetween(Date startDate, Date endDate, Pageable pageable) {
        List<ObjectiveResponse> responses = new ArrayList<>();

        Page<Objective> objectives = objectiveRepository.findByStartDateAndEndDateBetween(startDate, endDate, pageable);

        responses = ObjectiveMapper.INSTANCE.mapObjective(objectives.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", objectives.getNumber());
        page.put("totalElements", objectives.getTotalElements());
        page.put("totalPages", objectives.getTotalPages());

        return page;
    }

    @Override
    public Map<String, Object> searchByAllAttributes(String name, int year, Set<InterviewType> interviewTypes, Set<String> status, Set<Long> collaboratorsId, Pageable pageable) {
        List<ObjectiveResponse> responses = new ArrayList<>();
        if(collaboratorsId.isEmpty()){
            List<Collaborator> collaborators=collaboratorRepository.findAll();
            for (Collaborator collaborator:collaborators
            ) {
                collaboratorsId.add(collaborator.getId());
            }
        }

        Page<Objective> objectives = objectiveRepository.findByAllAttributes(name, year, interviewTypes, status, collaboratorsId, pageable);

        responses = ObjectiveMapper.INSTANCE.mapObjective(objectives.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", objectives.getNumber());
        page.put("totalElements", objectives.getTotalElements());
        page.put("totalPages", objectives.getTotalPages());

        return page;
    }
}
