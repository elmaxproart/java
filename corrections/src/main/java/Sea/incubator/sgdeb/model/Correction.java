package Sea.incubator.sgdeb.model;

import Sea.incubator.sgdeb.model.enumType.Nature;
import Sea.incubator.sgdeb.model.enumType.Semestre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Correction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double note;
    private String matricule;
    private String nom;
    private  Integer anonymat;
    private Semestre semestre;
    private String ue;
    private boolean status;
    private Nature nature;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    private LocalDateTime dateModification;

    private LocalDateTime dateSuppression;

}