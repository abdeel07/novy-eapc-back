package io.novelis.novyeapc.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {


    private Long id;

    private String question;

    private String answer;

    private Long interviewId;
}
