package io.novelis.novyeapc.models.responses;

import io.novelis.novyeapc.entities.enums.InterviewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponse {
    private Long id;

    private InterviewType type;

    private Date date;

    private String notice;

    private Set<ObjectiveResponse> objectives;

    private Set<FulfillmentResponse> fulfillments;

    private Set<QuizResponse> quizzes ;

    private Long collaboratorId;

    private String collaboratorName;

}
