package io.novelis.novyeapc.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorResponse {
    private Long id;

    private String code;

    private String firstName;

    private String lastName;

    private String title;

    private String phoneNumber;

    private String email;
}
