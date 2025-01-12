package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.model.DossierCandidat;
import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
public class ValiderDossierDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idDepot;

    private long codePreinscriptionDossier;
    private boolean isMoyenneValide=true;
    private boolean isDiplomeValide=true;
    private boolean isPreInscrit=true;
    private boolean isDocumentCertifier=true;
    private boolean isDossierComple=true;
    private boolean isFraisRegle=true;
    private boolean isIdentiteConfirmee=true;
}
