package Sea.incubator.sgdeb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
@Entity
@Data
@Table(name = "Validation")
public class ValiderDossier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idDepot;

    @OneToOne
    @JoinColumn(name = "id_dossierPhysique")
    private DossierCandidat dossierPhysique;
    private boolean isMoyenneValide=true;
    private boolean isDiplomeValide=true;
    private boolean isPreInscrit=true;
    private boolean isDocumentCertifier=true;
    private boolean isDossierComple=true;
    private boolean isFraisRegle=true;
    private boolean isIdentiteConfirmee=true;
    private boolean isValider=true;



}
