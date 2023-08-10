package io.novelis.novyeapc.models.responses;

import io.novelis.novyeapc.entities.enums.InterviewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectiveResponse {

    private Long id;

    private String title;

    private InterviewType interviewType;

    private Integer achievement;

    private Long collaboratorId;

    private String collaboratorName;

    private Long interviewId;

    private String comment;

    private String status;

    private Date startDate;

    private Date endDate;
}
