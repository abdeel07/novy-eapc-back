package io.novelis.novyeapc.mappers;

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

    Objective objectiveRequestToObjective(ObjectiveRequest objectiveRequest);

    @Mapping(source = "collaborator.id", target = "collaboratorId")
    ObjectiveResponse objectiveToObjectiveResponse(Objective objective);

    List<ObjectiveResponse> mapObjective(List<Objective> objectives);
}
