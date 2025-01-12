package Sea.incubator.sgdeb.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true,nullable = false)
    private String nomDepartment;

    @Column(nullable = false,unique = true)
    @CreationTimestamp
    private LocalDateTime dateCreation;
    @UpdateTimestamp
    private LocalDateTime dateModification;
    private LocalDateTime dateSuppression;

    @OneToMany(mappedBy = "departement",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Filiere> listFiliere;

}
