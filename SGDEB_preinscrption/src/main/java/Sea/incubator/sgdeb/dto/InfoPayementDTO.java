package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.model.DossierCandidat;
import Sea.incubator.sgdeb.model.enumType.AgencePaiement;
import Sea.incubator.sgdeb.model.enumType.Statut;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
@Data
public class InfoPayementDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPaiement;

    @OneToOne
    @JoinColumn(name = "idDossier", nullable = false)
    private DossierCandidat dossierCandidat;

    private String numeroTransaction;
    private String dateTransaction;
    private Double montant; // montant payé
    private Statut statut; // statut du paiement (ECHOUE,ATTENT,VALIDE)
    private AgencePaiement modePaiement;

}
