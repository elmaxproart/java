package Sea.incubator.sgdeb.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
public class FiliereDTO {
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
