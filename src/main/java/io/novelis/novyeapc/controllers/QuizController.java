package io.novelis.novyeapc.controllers;

import io.novelis.novyeapc.models.requests.QuizRequest;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import io.novelis.novyeapc.models.responses.QuizResponse;
import io.novelis.novyeapc.services.impl.InterviewServiceImpl;
import io.novelis.novyeapc.services.impl.QuizServiceImpl;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes/")
@CrossOrigin(origins = { "http://localhost:3000" })
public class QuizController {
    @Autowired
    QuizServiceImpl quizService;

    @Autowired
    InterviewServiceImpl interviewService;

    @PostMapping("/interview/{interview_id}/quiz")
    public ResponseEntity<QuizResponse> addQuiz(@PathVariable("interview_id") Long interview_id, @RequestBody QuizRequest quizRequest){
        InterviewResponse interviewResponse= interviewService.get(interview_id);
        if(interviewResponse==null){
            new ResourceNotFoundException("Not found interview="+interview_id);
        }

//        quizRequest.setInterview(interviewResponse);
        QuizResponse quizResponse=quizService.add(quizRequest);
        return new ResponseEntity<>(quizResponse, HttpStatus.CREATED);
    }

    @GetMapping("/interview/{interview_id}/quiz")
    public ResponseEntity<List<QuizResponse>> getQuiz(@PathVariable(value = "interview_id") Long interview_id){
//        if(!interviewService.existsById(interview_id)){
//            throw new ResourceNotFoundException("not found interview  with id "+interview_id);
//        }
        List<QuizResponse> quizResponses=quizService.getAll(interview_id);


        return new ResponseEntity<>(quizResponses,HttpStatus.OK);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizResponse> > getQuizzes(){
        List<QuizResponse> quizResponses=quizService.getAll();
        return new ResponseEntity<>(quizResponses,HttpStatus.OK);
    }
    @GetMapping("/quiz/{quiz_id}")
    public ResponseEntity<QuizResponse> getFulfillmentById(@PathVariable(value = "quiz_id") Long quiz_id){
        QuizResponse quizResponse=quizService.get(quiz_id);
        return  new ResponseEntity<>(quizResponse, HttpStatus.OK);

    }
    @PutMapping("/quiz/{quiz_id}")
    public ResponseEntity<QuizResponse> updateQuiz(@PathVariable(value = "quiz_id") Long quiz_id,@RequestBody QuizRequest quizRequest){
        QuizResponse quizResponse= quizService.update(quiz_id,quizRequest);
        return new ResponseEntity<>(quizResponse, HttpStatus.OK);

    }
    @PutMapping("")
    public ResponseEntity<List<QuizResponse>> updateQuizzes(@RequestBody List<QuizRequest> quizRequests){
        List<QuizResponse> quizResponses = new ArrayList<>();

        for (QuizRequest quizRequest : quizRequests) {

            QuizResponse quizResponse = quizService.update(quizRequest.getId(), quizRequest);
            quizResponses.add(quizResponse);
        }
        return new ResponseEntity<>(quizResponses, HttpStatus.OK);
    }

    @DeleteMapping("/quiz/{quiz_id}")
    public ResponseEntity<HttpStatus> deleteQuiz(@PathVariable(value = "quiz_id") Long quiz_id){

        quizService.delete(quiz_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}

