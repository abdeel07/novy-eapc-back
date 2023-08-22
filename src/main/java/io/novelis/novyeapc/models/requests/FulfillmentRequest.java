package io.novelis.novyeapc.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentRequest {
    private Long id;

    private String title;

    private String comment;

    private Long interviewId;
}
