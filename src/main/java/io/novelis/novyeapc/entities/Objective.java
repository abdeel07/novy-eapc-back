package io.novelis.novyeapc.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.novelis.novyeapc.entities.enums.InterviewType;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Objective implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewType interviewType;

    @Column(nullable = false)
    @Min(value = 0, message = "Value must be greater than or equal to 0")
    @Max(value = 100, message = "Value must be less than or equal to 100")
    private Integer achievement;

    private String comment;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "collaborator_id", nullable = false)
    @JsonBackReference(value="collaborator-objective")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collaborator collaborator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "interview_id")
    @JsonBackReference(value="interview-objective")
    private Interview interview;
}
