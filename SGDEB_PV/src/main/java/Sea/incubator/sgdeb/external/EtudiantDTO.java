package Sea.incubator.sgdeb.external;

import lombok.Data;

import java.util.UUID;

@Data
public class EtudiantDTO {
    private UUID etudiant_id;

    private String nomEtudiant;
    private String sexEtudiant;
    private String matricule;
    private String email;
    private String cycle;
    private int effectifFiliere;
}