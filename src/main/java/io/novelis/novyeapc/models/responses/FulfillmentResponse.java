package io.novelis.novyeapc.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentResponse {

    private Long id;

    private String title;

    private String comment;

    private Long interviewId;
}
