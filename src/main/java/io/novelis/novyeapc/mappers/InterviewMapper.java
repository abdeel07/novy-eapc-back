package io.novelis.novyeapc.mappers;

import io.novelis.novyeapc.entities.Collaborator;
import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.models.requests.InterviewRequest;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InterviewMapper {

    InterviewMapper INSTANCE = Mappers.getMapper(InterviewMapper.class);

    @Mapping(source = "collaboratorId", target = "collaborator.id")
    @Mapping(target = "fulfillments", ignore = true)
    @Mapping(target = "quizzes", ignore = true)
    Interview interviewRequestToInterview(InterviewRequest interviewRequest);

    @Mapping(source = "collaborator.id", target = "collaboratorId")
    @Mapping(source = "objectives", target = "objectives")
    @Mapping(source = "fulfillments", target = "fulfillments")
    @Mapping(source = "quizzes", target = "quizzes")
    @Mapping(source = "collaborator", target = "collaboratorName")
    InterviewResponse interviewToInterviewResponse(Interview interview);

    default String mapCollaboratorToCollaboratorName(Collaborator collaborator) {
        if (collaborator == null) {
            return null;
        }
        return collaborator.getFirstName() + " " + collaborator.getLastName();
    }

    List<InterviewResponse> mapInterview(List<Interview> interviews);
}
