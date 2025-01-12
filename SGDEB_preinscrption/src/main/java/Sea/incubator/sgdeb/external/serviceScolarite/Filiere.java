package Sea.incubator.sgdeb.external.serviceScolarite;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "Filiere")
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idFiliere;
    private String nomFiliere;
    private String cycle;
    @Column(unique = true)
    private String codeFiliere;
    private String description;
    private int nombreDePlaces;
}
