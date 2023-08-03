package io.novelis.novyeapc.models.requests;

import io.novelis.novyeapc.entities.enums.InterviewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectiveRequest {

    private String title;

    private InterviewType interviewType;

    private Integer achievement;

    private Long collaboratorId;

    private String comment;

    private String status;

    private LocalDate startDate;

    private LocalDate endDate;
}
