package io.novelis.novyeapc.services;

import io.novelis.novyeapc.models.requests.CollaboratorRequest;
import io.novelis.novyeapc.models.responses.CollaboratorResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CollaboratorService {

    List<CollaboratorResponse> getAll();

    CollaboratorResponse add(CollaboratorRequest collaboratorRequest);

    CollaboratorResponse get(Long id);

    CollaboratorResponse update(Long id, CollaboratorRequest collaboratorRequest);

    void delete(Long id);

    Map<String, Object> searchByLastName(String name, Pageable pageable);
}
