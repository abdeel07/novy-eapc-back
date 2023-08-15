package io.novelis.novyeapc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.novelis.novyeapc.entities.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


/**
 * A Collaborator.
 */
@Entity
@Table(name = "collaborator")
@JsonIgnoreProperties(value = {"code", "phoneNumber", "homeAddress", "relaisRh", "cnss", "rib",
        "location", "updatedAt", "roles","permission", "deletedAt",  "matricule", "position",
        "coefficient", "contractType", "collaboratorStatue", "statutProfessionnel", "department", "activities", "resume",
        "staffing", "absences", "collaborators", "tasks", "documents", "imputations", "validations", })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Collaborator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 190)
    @Column(name = "code", length = 190)
    private String code;

    @Size(max = 255)
    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    @Size(max = 255)
    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;

    @Size(max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Size(max = 255)
    @Column(name = "phone_number", length = 255, nullable = false)
    private String phoneNumber;

    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 255)
    @Column(name = "roles", length = 255)
    private String roles;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JsonIgnoreProperties("collaborators")
    private Collaborator relaisRh;

    @OneToMany(mappedBy = "relaisRh")
    private Set<Collaborator> collaborators = new HashSet<>();

    @JsonManagedReference(value="collaborator-interview")
    @OneToMany(mappedBy = "collaborator")
    private Set<Interview> interviews;

    @JsonManagedReference(value="collaborator-notification")
    @OneToMany(mappedBy = "collaborator")
    private Set<Notification> notifications;

}
