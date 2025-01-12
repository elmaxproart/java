package Sea.incubator.sgdeb.model;
import Sea.incubator.sgdeb.model.enumType.*;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "DossierCandidat")
public class DossierCandidat {

    // Informations à renseigner après le paiement des frais de préinscription

    @Id
    @GeneratedValue
    private UUID idDossier;

    // État Civil
    @Column(unique = true, updatable = false)
    private long codePaiement;
    private long codePreinscription;
    private String nomEtPrenom;
    private String sexeEtudiant;
    private String dateNaissance;
    private int age;
    private String lieuxNaissance;
    private String telePhone;
    private String email;
    private String adresse;
    private String statutMatrimonial;
    private String statutProfessionnel;
    private String premiereLangue;
    @Column(unique = true)
    private String numeroCNI;

    // Filiation et Infos d'Urgence

    private String regionOrigine;
    private String departement;
    private String nationalite;
    private String nomDuPere;
    private String professionPere;
    private String nomDeLaMere;
    private String professionMere;

    // Personne à Contacter
    private String nomContact;
    private String telContact;
    private String villeContact;

    // Informations à renseigner après le paiement des frais de préinscription

    // Filière
    private Etablissement faculte; // enum
    private Niveau niveau; // enum
    private String statut;
    private List<Choix> choix;

    // Infos Diplôme
    private String nomDiplome;
    private String typeDiplome;
    private String serieObtention;
    private String dateObtention;
    private Double moyenneObtention;
    private String observationJuridiqueMention; // enum
    private String emetteur;
    private String dateDelivrance;

    // Autres Détails
    // Infos Paiement
    // private boolean codeUtilise = false;
    @Column(unique = true)
    private String numeroTransaction;
    private AgencePaiement agence; // enum
    private long fraisPreinscription;

    // Information Diverses
    private PratiqueSport pratiqueSport; // enum oui ou non
    private PratiqueArt pratiqueArt; // oui ou non
    // Optionnel
    @Column(unique = true)
    private String numeroCertificatMedical;
    private String lieuxDuCertificat;
    private boolean admin;

}