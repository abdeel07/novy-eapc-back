package io.novelis.novyeapc.services.impl;

import io.novelis.novyeapc.entities.Fulfillment;
import io.novelis.novyeapc.entities.Quiz;
import io.novelis.novyeapc.mappers.InterviewMapper;
import io.novelis.novyeapc.mappers.QuizMapper;
import io.novelis.novyeapc.models.requests.QuizRequest;
import io.novelis.novyeapc.models.responses.FulfillmentResponse;
import io.novelis.novyeapc.models.responses.QuizResponse;
import io.novelis.novyeapc.repositories.QuizRepository;
import io.novelis.novyeapc.services.QuizService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuizServiceImpl implements QuizService {
    QuizMapper mapper = Mappers.getMapper(QuizMapper.class);
    @Autowired
    QuizRepository quizRepository;


    @Override
    public List<QuizResponse> getAll() {
        List<Quiz> quizzes=quizRepository.findAll();
        List<QuizResponse> quizResponses=mapper.mapQuiz(quizzes);
        return quizResponses;
    }

    @Override
    public QuizResponse add(QuizRequest quizRequest) {
        Quiz quiz=mapper.quizRequestToQuiz(quizRequest);

        Quiz quiz1=quizRepository.save(quiz);
        QuizResponse quizResponse=mapper.quizToQuizResponse(quiz1);


        return quizResponse;
    }

    @Override
    public QuizResponse get(Long id) {
        Quiz quiz=quizRepository.findById(id).get();
        QuizResponse quizResponse=mapper.quizToQuizResponse(quiz);
        return quizResponse;
    }

    @Override
    public QuizResponse update(Long id, QuizRequest quizRequest) {
        Quiz quiz=quizRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("interview "+id+"not found"));

        quiz.setAnswer(quizRequest.getAnswer());
        quiz.setQuestion(quizRequest.getQuestion());
        System.out.println(quizRequest.getAnswer());
        Quiz quiz1=quizRepository.save(quiz);
        QuizResponse quizResponse=mapper.quizToQuizResponse(quiz1);
        System.out.println(quiz1.getAnswer());
        return  quizResponse;
    }

    @Override
    public void delete(Long id) {
        Quiz quiz=quizRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("interview "+id+"not found"));
        quizRepository.deleteById(id);
    }

    @Override
    public List<QuizResponse> getAll(Long id) {
        List<Quiz> quizzes=quizRepository.findByInterviewId(id);
        List<QuizResponse> interviewResponses=mapper.mapQuiz(quizzes);
        return interviewResponses;
    }
}
