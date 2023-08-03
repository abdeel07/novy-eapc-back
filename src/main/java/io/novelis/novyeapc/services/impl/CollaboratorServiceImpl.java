package io.novelis.novyeapc.services.impl;

import io.novelis.novyeapc.entities.Collaborator;
import io.novelis.novyeapc.mappers.CollaboratorMapper;
import io.novelis.novyeapc.models.requests.CollaboratorRequest;
import io.novelis.novyeapc.models.responses.CollaboratorResponse;
import io.novelis.novyeapc.repositories.CollaboratorRepository;
import io.novelis.novyeapc.services.CollaboratorService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Override
    public List<CollaboratorResponse> getAll() {
        return CollaboratorMapper.INSTANCE.mapCollaborator(collaboratorRepository.findAll());
    }

    @Override
    public CollaboratorResponse add(CollaboratorRequest collaboratorRequest) {
        Collaborator collaborator = CollaboratorMapper.INSTANCE.collaboratorRequestToCollaborator(collaboratorRequest);

        return CollaboratorMapper.INSTANCE.collaboratorToCollaboratorResponse
                (collaboratorRepository.save(collaborator));
    }

    @Override
    public CollaboratorResponse get(Long id) {
        Optional<Collaborator> collaborator = collaboratorRepository.findById(id);
//
//        if(!collaborator.isPresent())
//            throw new ResourceNotFoundException(id + " doesn't exist !");

        return CollaboratorMapper.INSTANCE.collaboratorToCollaboratorResponse
                (collaborator.get());
    }

    @Override
    public CollaboratorResponse update(Long id, CollaboratorRequest collaboratorRequest) {
        Optional<Collaborator> findCollaborator = collaboratorRepository.findById(id);

        if(!findCollaborator.isPresent())
            return null;

        Collaborator collaborator = findCollaborator.get();
        collaborator.setFirstName(collaboratorRequest.getFirstName());
        collaborator.setLastName(collaboratorRequest.getLastName());
        //...

        return CollaboratorMapper.INSTANCE.collaboratorToCollaboratorResponse
                (collaboratorRepository.save(collaborator));
    }

    @Override
    public void delete(Long id) {
        Optional<Collaborator> findCollaborator = collaboratorRepository.findById(id);

        if(!findCollaborator.isPresent()){
            //throw new exception
        }

        collaboratorRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> searchByLastName(String name, Pageable pageable) {
        List<CollaboratorResponse> responses = new ArrayList<>();

        Page<Collaborator> collaborators = (name.isEmpty()) ? collaboratorRepository.findAll(pageable)
                : collaboratorRepository.findByLastNameContainingIgnoreCase(name, pageable);

        responses = CollaboratorMapper.INSTANCE.mapCollaborator(collaborators.toList());

        Map<String, Object> page = new HashMap<>();
        page.put("content", responses);
        page.put("currentPage", collaborators.getNumber());
        page.put("totalElements", collaborators.getTotalElements());
        page.put("totalPages", collaborators.getTotalPages());

        return page;
    }
}
