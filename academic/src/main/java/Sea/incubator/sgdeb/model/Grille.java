package Sea.incubator.sgdeb.model;

import Sea.incubator.sgdeb.model.enumType.Niveau;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Grille {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String libelle;
    private Niveau niveau;
    @ManyToOne
    @JoinColumn(name = "annee_id")
    private AnneeAcademic anneeAcademic;
    @Column(nullable = false,unique = true)
    private String code;

    @OneToMany(mappedBy = "grille",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UE> listUe;
    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime dateCreation;
    @UpdateTimestamp
    private LocalDateTime dateModification;
    private LocalDateTime dateSuppression;



}
