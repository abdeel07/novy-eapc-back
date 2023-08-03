package io.novelis.novyeapc.mappers;

import io.novelis.novyeapc.entities.Collaborator;
import io.novelis.novyeapc.models.requests.CollaboratorRequest;
import io.novelis.novyeapc.models.responses.CollaboratorResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CollaboratorMapper {

    CollaboratorMapper INSTANCE = Mappers.getMapper(CollaboratorMapper.class);

    Collaborator collaboratorRequestToCollaborator(CollaboratorRequest collaboratorRequest);

    CollaboratorResponse collaboratorToCollaboratorResponse(Collaborator collaborator);

    List<CollaboratorResponse> mapCollaborator(List<Collaborator> collaborators);

}
