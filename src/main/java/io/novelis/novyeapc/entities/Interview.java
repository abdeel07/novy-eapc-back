package io.novelis.novyeapc.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.novelis.novyeapc.entities.enums.InterviewType;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewType type;

    @Column(nullable = false)
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

    private String notice;

    @OneToMany(mappedBy = "interview")
    @JsonManagedReference(value="interview-objective")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Objective> objectives;

    @OneToMany(mappedBy = "interview")
    @JsonManagedReference(value="interview-fulfillment")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Fulfillment> fulfillments;

    @ManyToOne
    @JoinColumn(name = "collaborator_id", referencedColumnName = "id",nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collaborator collaborator;


    @OneToMany(mappedBy = "interview"     )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Quiz> quizzes;

}