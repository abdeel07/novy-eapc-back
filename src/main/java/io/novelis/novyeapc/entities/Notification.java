package io.novelis.novyeapc.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "collaborator_id", nullable = false)
    @JsonBackReference(value="collaborator-notification")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collaborator collaborator;

    @PrePersist
    private void onCreate() {
        this.date = new Date();
    }
}
