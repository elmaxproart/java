package Sea.incubator.sgdeb.model;

import Sea.incubator.sgdeb.model.enumType.Semestre;
import Sea.incubator.sgdeb.model.enumType.TypeUE;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class UE {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String libelle;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private int credits;
    @Column(nullable = false)
    private int volumeHoraire;
    @Column(nullable = false)
    private Semestre semestre;
    @Column(nullable = false)
    private TypeUE typeUE;
    private int pourcentageTp;
    private  int pourcentageCC;
    private int pourcentageSN;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime dateCreation;
    @UpdateTimestamp
    private LocalDateTime dateModification;
    private LocalDateTime dateSuppression;

    @ManyToOne
    @JoinColumn(name = "grille_id",nullable = false)
    private Grille grille;


    @OneToMany(mappedBy = "UE",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EC> listEc;


}
