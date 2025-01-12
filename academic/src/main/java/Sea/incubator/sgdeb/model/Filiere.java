package Sea.incubator.sgdeb.model;

import Sea.incubator.sgdeb.model.enumType.Faculte;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,unique = true)
    private String libelle;
    @Column(nullable = false,unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "departement_id",nullable = false)
    private Departement departement;
    @OneToMany(mappedBy = "filiere",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Grille> grilleList;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime dateCreation;
    @UpdateTimestamp
    private LocalDateTime dateModification;
    private LocalDateTime dateSuppression;

    private Faculte faculte;
    private int effectif;


}
