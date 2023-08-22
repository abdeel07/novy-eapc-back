package io.novelis.novyeapc.mappers;

import io.novelis.novyeapc.entities.Fulfillment;
import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.models.requests.FulfillmentRequest;
import io.novelis.novyeapc.models.responses.FulfillmentResponse;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FulfillmentMapper {

    FulfillmentMapper INSTANCE = Mappers.getMapper(FulfillmentMapper.class);
    InterviewMapper interviewMapper = InterviewMapper.INSTANCE;

    @Mapping(target = "interview.id", source="interviewId")
    Fulfillment fulfillmentRequestToFulfillment(FulfillmentRequest fulfillmentRequest);

    @Mapping(source = "interview.id", target = "interviewId")
    FulfillmentResponse fulfillmentToFulfillmentResponse(Fulfillment fulfillment);

    List<FulfillmentResponse> mapFulfillment(List<Fulfillment> fulfillments);
    Set<FulfillmentResponse> fulfillmentSetToFulfillmentResponseSet(Set<Fulfillment> set);
   /* @Mapping(target = "fulfillments" ,ignore = true)
    @Mapping(target = "quizzes",ignore = true)

    InterviewResponse interviewToInterviewResponse(Interview interview);*/

}
