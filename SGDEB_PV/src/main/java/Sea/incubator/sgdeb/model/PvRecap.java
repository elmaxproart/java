package Sea.incubator.sgdeb.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class PvRecap {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String matricule;
    private String nom;
    private String niveaux;
    private double mgp;
    private String decision;
    private String mention;
    private double pourcentageCap;
    private double moyenne;
    private double noteSur100;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "observation_id")
    private Observation observation;
    private String codeFil;
    private String annee;


    // Pour stocker les notes avec clé comme type d'évaluation + UE et la note correspondante
    @ElementCollection
    @CollectionTable(name = "pv_notes", joinColumns = @JoinColumn(name = "pv_id"))
    @MapKeyColumn(name = "evaluation_type")
    @Column(name = "note")
    private Map<String, Double> notes = new HashMap<>();

    // Pour stocker les notes sur 100 pour chaque UE

    @CollectionTable(name = "pv_ue_grades_sur100", joinColumns = @JoinColumn(name = "pv_id"))
    @MapKeyColumn(name = "ue_code")
    @Column(name = "note_sur100")
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Double> ueGradesSur100 = new HashMap<>();

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    private LocalDateTime delectAt;
}
