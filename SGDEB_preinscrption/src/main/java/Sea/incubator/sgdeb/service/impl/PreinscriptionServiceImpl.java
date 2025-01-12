package Sea.incubator.sgdeb.service.impl;


import Sea.incubator.sgdeb.external.servicePaiement.PayementP;
import Sea.incubator.sgdeb.model.DossierCandidat;
import Sea.incubator.sgdeb.model.ValiderDossier;
import Sea.incubator.sgdeb.repository.DossierRepository;
import Sea.incubator.sgdeb.service.Preinscriptionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class PreinscriptionServiceImpl implements Preinscriptionservice {

    private final DossierRepository dossierRepository;
    private final DossierService dossierService;
    private final RestTemplate restTemplate;


    

    @Autowired
    public PreinscriptionServiceImpl(DossierRepository dossierRepository, DossierService dossierService, RestTemplate restTemplate) {
        this.dossierRepository = dossierRepository;
        this.dossierService = dossierService;
        this.restTemplate = restTemplate;
    }

    public Boolean validerPaiement(String codeBank) {
        String paiementApiUrl = "http://localhost:8000/api/py/paiement/verifier?codeBank=" + codeBank;

        try {
            ResponseEntity<PayementP> response = restTemplate.getForEntity(paiementApiUrl, PayementP.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody() != null;
            } else {

                System.err.println("Erreur: Paiement non trouvé pour le code bancaire donné.");
                return false;
            }

        } catch (HttpClientErrorException e) {
            // Gérer les erreurs de client (4xx)
            System.err.println("Erreur de client : " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return false;

        } catch (HttpServerErrorException e) {
            // Gérer les erreurs de serveur (5xx)
            System.err.println("Erreur de serveur : " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return false;

        } catch (ResourceAccessException e) {
            // Gérer les erreurs d'accès aux ressources (ex: service non disponible)
            System.err.println("Erreur d'accès : " + e.getMessage());
            return false;

        } catch (RestClientException e) {
            // Gérer les autres erreurs de RestTemplate
            System.err.println("Erreur de requête : " + e.getMessage());
            return false;
        }
    }
    @Override
    public Void validationDossier(UUID id) {
        if (id != null) {
            System.out.println("ID fourni : " + id);
            boolean exists = dossierRepository.existsById(id);
            System.out.println("Dossier existe : " + exists);

            if (exists) {
                DossierCandidat dossierCandidat = dossierRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Dossier non trouvé pour l'ID : " + id));

                ValiderDossier dossierPhysique = new ValiderDossier();
                // Debugging statements


                System.out.println("Dossier Comple: " + dossierPhysique.isDossierComple());
                System.out.println("Diplome Valide: " + dossierPhysique.isDiplomeValide());
                System.out.println("Moyenne Valide: " + dossierPhysique.isMoyenneValide());
                System.out.println("Document Certifier: " + dossierPhysique.isDocumentCertifier());
                System.out.println("Identite Confirmee: " + dossierPhysique.isIdentiteConfirmee());
                System.out.println("Pre Inscrit: " + dossierPhysique.isPreInscrit());

                System.out.println("Frais Regle: " + dossierPhysique.isFraisRegle());
                if (dossierPhysique.isFraisRegle() &&
                        dossierPhysique.isDossierComple() &&
                        dossierPhysique.isDiplomeValide() &&
                        dossierPhysique.isMoyenneValide() &&
                        dossierPhysique.isDocumentCertifier() &&
                        dossierPhysique.isIdentiteConfirmee() &&
                        dossierPhysique.isPreInscrit()) {

                    dossierPhysique.setValider(true);
                    System.out.println("Dossier validé avec succès.");
                } else {
                    throw new RuntimeException("L'étudiant ne peut être validé, il ne respecte pas certains critères de validation.");
                }
            } else {
                throw new RuntimeException("Dossier non trouvé pour l'ID : " + id);
            }
        } else {
            throw new RuntimeException("ID non fourni !");
        }
        return null;
    }
    @Override
    public Void SelectionCandidat(UUID id) {
        // Récupérer le dossier par ID
        DossierCandidat dossierCandidat = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé"));

        int ageStandard = 15;
        Double noteStandard = 10.00;

        // Vérification de l'âge et de la moyenne
        if (dossierCandidat.getAge() < ageStandard || dossierCandidat.getMoyenneObtention() < noteStandard) {
            throw new RuntimeException("Dossier error: (âge ou moyenne) invalide. Impossible de sélectionner ce dossier.");
        }

        // Récupérer les pourcentages par région
        Map<String, Double> pourcentages = dossierService.calculateRegionPercentages();

        // Obtenir la région en minuscules
        String region = dossierCandidat.getRegionOrigine().toLowerCase();
        boolean admis = true; // Variable pour indiquer si le dossier est admis

        // Vérifier si les pourcentages sont atteints pour chaque région
        switch (region) {
            case "centre":
                if (pourcentages.getOrDefault("centre", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Centre a été atteinte.");
                }
                break;
            case "littoral":
                if (pourcentages.getOrDefault("littoral", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Littoral a été atteinte.");
                }
                break;
            case "ouest":
                if (pourcentages.getOrDefault("ouest", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Ouest a été atteinte.");
                }
                break;
            case "nord-ouest":
                if (pourcentages.getOrDefault("nord-ouest", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Nord-Ouest a été atteinte.");
                }
                break;
            case "nord":
                if (pourcentages.getOrDefault("nord", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Nord a été atteinte.");
                }
                break;
            case "sud-ouest":
                if (pourcentages.getOrDefault("sud-ouest", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Sud-Ouest a été atteinte.");
                }
                break;
            case "sud":
                if (pourcentages.getOrDefault("sud", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Sud a été atteinte.");
                }
                break;
            case "est":
                if (pourcentages.getOrDefault("est", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Est a été atteinte.");
                }
                break;
            case "adamaoua":
                if (pourcentages.getOrDefault("adamaoua", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Adamaoua a été atteinte.");
                }
                break;
            case "centre-nord":
                if (pourcentages.getOrDefault("centre-nord", 0.0) >= 100.0) {
                    throw new RuntimeException("La limite de sélection pour la région Centre-Nord a été atteinte.");
                }
                break;
            default:
                throw new RuntimeException("Région inconnue : " + dossierCandidat.getRegionOrigine());
        }

        // Si aucune limite n'est atteinte, le dossier est admis
        dossierCandidat.setAdmin(true);
        System.out.println("le dossier a ete selectionnee avec succes....!");
        dossierRepository.save(dossierCandidat);

        return null;
    }
    /*
     * Fonction de calcul du score basé sur la moyenne et l'âge :
     *
     * Variables :
     * - m : moyenne (entre 10 et 20)
     * - a : âge (entre 15 et 35)
     *
     * Formule :
     * score = (m - 10) * 5 - (a - 25) * 2
     *
     * Explications :
     * - La moyenne est normalisée à 10. Chaque point au-dessus de 10 ajoute des points au score.
     * - L'âge est pénalisé à partir de 25 ans. Chaque année au-dessus de 25 retire 2 points.
     * - Si l'âge est inférieur à 15, une pénalité supplémentaire peut être appliquée.
     * - Si l'âge dépasse 35 ou si la moyenne est hors limites, un score de zéro peut être retourné.
     */
    //soit
    public void filtrerCandidats(List<DossierCandidat> candidats) {
        List<DossierCandidat> candidatsEligible = candidats.stream()
                .map(candidat -> {
                    int age = candidat.getAge();
                    Double moyenne = candidat.getMoyenneObtention();

                    if (moyenne == null) {

                        return null;
                    }

                    double score = calculerScore(age, moyenne);

                    // Fixer le score dans l'intervalle de 10 à 20
                    score = Math.max(10, Math.min(score, 20));

                    return new AbstractMap.SimpleEntry<>(candidat, score);
                })
                .filter(Objects::nonNull) // Filtrer les résultats null
                .filter(entry -> entry.getValue() >= 10) // Filtrer ceux qui ont un score >= 10
                .map(Map.Entry::getKey) // Récupérer seulement les candidats
                .collect(Collectors.toList());

        // Traitement des candidats éligibles
        for (DossierCandidat candidat : candidatsEligible) {
            // Logique pour chaque candidat éligible
        }
    }
    private double calculerScore(int age, double moyenne) {
        return 10 + ((moyenne - 10) * 0.5) - ((age - 25) * 0.5);
    }

}
