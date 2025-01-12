package Sea.incubator.sgdeb.external.servicePaiement;

import Sea.incubator.sgdeb.model.DossierCandidat;
import Sea.incubator.sgdeb.model.enumType.AgencePaiement;
import Sea.incubator.sgdeb.model.enumType.Statut;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "InfoPaiement")
public class InfoPayement {


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
