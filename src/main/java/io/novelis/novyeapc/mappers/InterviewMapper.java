package io.novelis.novyeapc.mappers;

import io.novelis.novyeapc.entities.Collaborator;
import io.novelis.novyeapc.entities.Fulfillment;
import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.models.requests.FulfillmentRequest;
import io.novelis.novyeapc.models.requests.InterviewRequest;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InterviewMapper {

    InterviewMapper INSTANCE = Mappers.getMapper(InterviewMapper.class);

    @Mapping(target = "fulfillments", source = "fulfillments")
    @Mapping(target = "quizzes", source = "quizzes")
    @Mapping(target = "objectives", ignore = true)
    Interview interviewRequestToInterview(InterviewRequest interviewRequest);

    default Set<Fulfillment> mapFulfillmentRequestsToEntities(Set<FulfillmentRequest> fulfillmentRequests) {
        if (fulfillmentRequests == null) {
            return null;
        }

        Set<Fulfillment> fulfillments = new HashSet<>();
        for (FulfillmentRequest fulfillmentRequest : fulfillmentRequests) {
            Fulfillment fulfillment = FulfillmentMapper.INSTANCE.fulfillmentRequestToFulfillment(fulfillmentRequest);

            fulfillments.add(fulfillment);
        }
        return fulfillments;
    }

    @Mapping(source = "collaborator.id", target = "collaboratorId")
    @Mapping(source = "objectives", target = "objectives")
    @Mapping( target = "fulfillments",source="fulfillments")

    @Mapping(target = "quizzes",source = "quizzes")
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
