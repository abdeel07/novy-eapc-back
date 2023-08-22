package io.novelis.novyeapc.mappers;


import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.entities.Quiz;
import io.novelis.novyeapc.models.requests.QuizRequest;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import io.novelis.novyeapc.models.responses.QuizResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuizMapper {
    QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);
    InterviewMapper interviewMapper = InterviewMapper.INSTANCE;
//    @Mapping(target = "interview" ,source = "interview")
    Quiz quizRequestToQuiz(QuizRequest quizRequest);

    @Mapping(source = "interview.id", target = "interviewId")
    QuizResponse quizToQuizResponse(Quiz quiz);

    List<QuizResponse> mapQuiz(List<Quiz> quizzes);
    Set<QuizResponse> quizSetToQuizResponseSet(Set<Quiz> set);
   /* @Mapping(target = "fulfillments" ,ignore = true)
    @Mapping(target = "quizzes",ignore = true)
    InterviewResponse interviewToInterviewResponse(Interview interview);*/
}
