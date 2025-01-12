package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.model.Code;
import Sea.incubator.sgdeb.model.DossierCandidat;
import Sea.incubator.sgdeb.repository.CodeRepository;
import Sea.incubator.sgdeb.repository.DossierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DossierService {
    private  final DossierRepository dossierRepository;
    private final CodeRepository codeRepository;

    @Autowired
    public DossierService(DossierRepository dossierRepository ,CodeRepository codeRepository) {
        this.dossierRepository = dossierRepository;
        this.codeRepository=codeRepository;
    }

    public List<DossierCandidat> getAllDossiers() {
        return dossierRepository.findAll();
    }


    public DossierCandidat getDossierById(UUID id) {
        return dossierRepository.findById(id).orElse(null);
    }
    
    public void deleteDossier(UUID id) {
        dossierRepository.deleteById(id);
    }
    public DossierCandidat createDossier(DossierCandidat dossierCandidatEntity) {

        long code = codePaiement();
        long code1=codePaiement();
        dossierCandidatEntity.setCodePreinscription(code);
        dossierCandidatEntity.setCodePaiement(code1);
        return dossierRepository.save(dossierCandidatEntity);
    }

    Code code=null;

    private long codePaiement() {
        long code;
        int maxAttempts = 10; // Limite d'essai
        int attempts = 0;

        do {
            code = generateUniqueCode(); // Générer un nouveau code
            attempts++;
            if (attempts >= maxAttempts) {
                throw new RuntimeException("Impossible de générer un code unique après plusieurs tentatives.");
            }
        } while (codeRepository.findByCodePaiement(code).isPresent());

        // Avant de sauvegarder, verifiez que le code n'a pas déjà été utilisé
        if (codeRepository.findByCodePaiement(code).isPresent()) {
            throw new RuntimeException("Le code généré est déjà présent dans la base de données.");
        }

        // Enregistrer le code avec une date d'expiration
        Code newCode = new Code();
        newCode.setCodePaiement(code);
        newCode.setDateExpiration(System.currentTimeMillis() + 604800000); // 1 semaine
        newCode.setUtilise(false); // Indiquer que le code n'a pas encore été utilisé

        // Sauvegarder le code
        codeRepository.save(newCode);

        return code;
    }


    private long generateUniqueCode() {
        Random random = new Random();
        return 1000000000000L + Math.abs(random.nextLong() % 9000000000000L);
    }

    public void utiliserCode(long code) {
        Optional<Code> codeOpt = codeRepository.findByCodePaiement(code);

        if (codeOpt.isPresent()) {
            Code existingCode = codeOpt.get();

            if (!existingCode.isUtilise() && !isCodeExpired(existingCode)) {
                existingCode.setUtilise(true); // Marquer comme utilisé
                codeRepository.save(existingCode);
            } else {
                throw new RuntimeException("Le code est déjà utilisé ou expiré.");
            }
        } else {
            throw new RuntimeException("Code non trouvé.");
        }
    }

    private boolean isCodeExpired(Code code) {
        return System.currentTimeMillis() > code.getDateExpiration();
    }


    
    public DossierCandidat updateDossier(UUID id, DossierCandidat dossierCandidatEntity) {
        // Vérifier si le code existe
        DossierCandidat existingDossierCandidat = dossierRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Dossier not found with id: " + id));
        
        
        existingDossierCandidat.setNomEtPrenom(dossierCandidatEntity.getNomEtPrenom());
        existingDossierCandidat.setSexeEtudiant(dossierCandidatEntity.getSexeEtudiant());
        existingDossierCandidat.setDateNaissance(dossierCandidatEntity.getDateNaissance());
        existingDossierCandidat.setAge(dossierCandidatEntity.getAge());
        existingDossierCandidat.setLieuxNaissance(dossierCandidatEntity.getLieuxNaissance());
        existingDossierCandidat.setTelePhone(dossierCandidatEntity.getTelePhone());
        existingDossierCandidat.setEmail(dossierCandidatEntity.getEmail());
        existingDossierCandidat.setAdresse(dossierCandidatEntity.getAdresse());
        existingDossierCandidat.setStatutMatrimonial(dossierCandidatEntity.getStatutMatrimonial());
        existingDossierCandidat.setStatutProfessionnel(dossierCandidatEntity.getStatutProfessionnel());
        existingDossierCandidat.setPremiereLangue(dossierCandidatEntity.getPremiereLangue());
        existingDossierCandidat.setRegionOrigine(dossierCandidatEntity.getRegionOrigine());
        existingDossierCandidat.setDepartement(dossierCandidatEntity.getDepartement());
        existingDossierCandidat.setNationalite(dossierCandidatEntity.getNationalite());
        existingDossierCandidat.setNomDuPere(dossierCandidatEntity.getNomDuPere());
        existingDossierCandidat.setProfessionPere(dossierCandidatEntity.getProfessionPere());
        existingDossierCandidat.setNomDeLaMere(dossierCandidatEntity.getNomDeLaMere());
        existingDossierCandidat.setProfessionMere(dossierCandidatEntity.getProfessionMere());
        existingDossierCandidat.setNomContact(dossierCandidatEntity.getNomContact());
        existingDossierCandidat.setTelContact(dossierCandidatEntity.getTelContact());
        existingDossierCandidat.setVilleContact(dossierCandidatEntity.getVilleContact());
        existingDossierCandidat.setFaculte(dossierCandidatEntity.getFaculte());
        existingDossierCandidat.setNiveau(dossierCandidatEntity.getNiveau());
        existingDossierCandidat.setStatut(dossierCandidatEntity.getStatut());
        existingDossierCandidat.setChoix(dossierCandidatEntity.getChoix());
        existingDossierCandidat.setNomDiplome(dossierCandidatEntity.getNomDiplome());
        existingDossierCandidat.setTypeDiplome(dossierCandidatEntity.getTypeDiplome());
        existingDossierCandidat.setSerieObtention(dossierCandidatEntity.getSerieObtention());
        existingDossierCandidat.setDateObtention(dossierCandidatEntity.getDateObtention());
        existingDossierCandidat.setMoyenneObtention(dossierCandidatEntity.getMoyenneObtention());
        existingDossierCandidat.setObservationJuridiqueMention(dossierCandidatEntity.getObservationJuridiqueMention());
        existingDossierCandidat.setEmetteur(dossierCandidatEntity.getEmetteur());
        existingDossierCandidat.setDateDelivrance(dossierCandidatEntity.getDateDelivrance());
        existingDossierCandidat.setAgence(dossierCandidatEntity.getAgence());
        existingDossierCandidat.setFraisPreinscription(dossierCandidatEntity.getFraisPreinscription());
        existingDossierCandidat.setPratiqueSport(dossierCandidatEntity.getPratiqueSport());
        existingDossierCandidat.setPratiqueArt(dossierCandidatEntity.getPratiqueArt());
        existingDossierCandidat.setLieuxDuCertificat(dossierCandidatEntity.getLieuxDuCertificat());

        // Sauvegarder les changements
        return dossierRepository.save(existingDossierCandidat);
    }

    public DossierCandidat getDossierByCodePreinscription(long code) {
        return dossierRepository.findByCodePreinscription(code).orElse(null);
    }

    public Map<String, Double> calculateRegionPercentages() {
        List<Object[]> results = dossierRepository.countCandidatsByRegionOrigine();

        double totalCandidats = results.stream()
                .mapToDouble(arr -> (Long) arr[1])
                .sum();

        Map<String, Double> percentages = new HashMap<>();
        for (Object[] result : results) {
            String region = (String) result[0];
            long count = (Long) result[1];
            double percentage = (count / totalCandidats) * 100;
            percentages.put(region, percentage);
        }

        return percentages;
    }


}
