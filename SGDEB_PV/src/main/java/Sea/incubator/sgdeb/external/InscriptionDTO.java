package Sea.incubator.sgdeb.external;

import lombok.Data;

import java.util.UUID;

@Data
public class InscriptionDTO {
    private UUID inscription_id;
    private String annee;
    private EtudiantDTO etudiantDTO;
}
