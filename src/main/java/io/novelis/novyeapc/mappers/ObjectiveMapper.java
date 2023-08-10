package io.novelis.novyeapc.mappers;

import io.novelis.novyeapc.entities.Collaborator;
import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.entities.Objective;
import io.novelis.novyeapc.models.requests.ObjectiveRequest;
import io.novelis.novyeapc.models.responses.ObjectiveResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ObjectiveMapper {

    ObjectiveMapper INSTANCE = Mappers.getMapper(ObjectiveMapper.class);

    @Mapping(target = "collaborator", source = "collaboratorId")
    @Mapping(target = "interview", source = "interviewId")
    Objective objectiveRequestToObjective(ObjectiveRequest objectiveRequest);

    Collaborator mapToCollaborator(Long collaboratorId);

    Interview mapToInterview(Long interviewId);

    @Mapping(source = "collaborator.id", target = "collaboratorId")
    @Mapping(source = "interview.id", target = "interviewId")
    @Mapping(source = "collaborator", target = "collaboratorName")
    ObjectiveResponse objectiveToObjectiveResponse(Objective objective);

    default String mapCollaboratorToCollaboratorName(Collaborator collaborator) {
        if (collaborator == null) {
            return null;
        }
        return collaborator.getFirstName() + " " + collaborator.getLastName();
    }

    List<ObjectiveResponse> mapObjective(List<Objective> objectives);
}
