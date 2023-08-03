package io.novelis.novyeapc.services;

import io.novelis.novyeapc.models.requests.FulfillmentRequest;
import io.novelis.novyeapc.models.requests.QuizRequest;
import io.novelis.novyeapc.models.responses.FulfillmentResponse;
import io.novelis.novyeapc.models.responses.QuizResponse;

import java.util.List;

public interface QuizService {
    List<QuizResponse> getAll(Long id);
    List<QuizResponse> getAll();


    QuizResponse add(QuizRequest quizRequest);

    QuizResponse get(Long id);

    QuizResponse update(Long id, QuizRequest quizRequest);

    void delete(Long id);
}
