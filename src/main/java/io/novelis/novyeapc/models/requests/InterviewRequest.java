package io.novelis.novyeapc.models.requests;

import io.novelis.novyeapc.entities.Objective;
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
public class InterviewRequest {

    private InterviewType type;

    private Date date;

    private String notice;

    private Set<QuizRequest> quizzes;

    private Set<FulfillmentRequest> fulfillments;

    private Set<Long> objectivesId;

    private Long collaboratorId;

}
