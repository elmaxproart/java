package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.model.DossierCandidat;
import Sea.incubator.sgdeb.model.ValiderDossier;
import Sea.incubator.sgdeb.repository.CodeRepository;
import Sea.incubator.sgdeb.repository.DossierRepository;
import Sea.incubator.sgdeb.repository.ValiderDossierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ValiderDossierService {
    private final ValiderDossierRepository validerDossierRepository;
    private final DossierRepository dossierRepository;
    private final PreinscriptionServiceImpl preinscriptionService;
    private final CodeRepository codeRepository;

    @Autowired
    public ValiderDossierService(ValiderDossierRepository validerDossierRepository, DossierRepository dossierRepository, PreinscriptionServiceImpl preinscriptionService,
                                 CodeRepository codeRepository) {
        this.validerDossierRepository = validerDossierRepository;
        this.dossierRepository = dossierRepository;
        this.preinscriptionService = preinscriptionService;
        this.codeRepository = codeRepository;
    }

    public List<ValiderDossier> getAllValidations() {
        return validerDossierRepository.findAll();
    }

    public ValiderDossier getValidationById(UUID id) {
        return validerDossierRepository.findById(id).orElse(null);
    }

    public ValiderDossier createValidation(UUID idDossierPhysique, String codeBank, ValiderDossier validerDossier, boolean valide1) {

        boolean valider =false;
              valider  = preinscriptionService.validerPaiement(codeBank);

        if (valider) {
            throw new RuntimeException("Vous avez une absence de paiement de frais de préinscription.");
        }

        ValiderDossier dossierPhysique = new ValiderDossier();
        if (!valide1) {
            dossierPhysique.setFraisRegle(false);
            dossierPhysique.setDiplomeValide(false);
            dossierPhysique.setMoyenneValide(false);
            dossierPhysique.setDocumentCertifier(false);
            dossierPhysique.setIdentiteConfirmee(false);
            dossierPhysique.setPreInscrit(false);
            dossierPhysique.setDossierComple(false);
        }

        preinscriptionService.validationDossier(idDossierPhysique);
        preinscriptionService.SelectionCandidat(idDossierPhysique);
        validerDossier.setValider(true);


       Optional<DossierCandidat> optionalDossier = dossierRepository.findById(idDossierPhysique);
       if (optionalDossier.isPresent()) {
           DossierCandidat dossierCandidat = optionalDossier.get();
           validerDossier.setDossierPhysique(dossierCandidat);

           // Mettre a jour le dossier dans la base de données
           dossierCandidat.setAdmin(true);
           // Mettre a jour le dossier dans la base de données
           dossierRepository.updateAdminBy(true);
           validerDossier.setDossierPhysique(validerDossier.getDossierPhysique());
       }else {
           throw new RuntimeException("Dossier non trouvé pour l'ID : " + idDossierPhysique);
       }


        return validerDossierRepository.save(validerDossier);
    }


    public ValiderDossier updateValidation(UUID id, ValiderDossier validerDossier) {
        ValiderDossier existingValidation = validerDossierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Validation not found with id: " + id));

        // Ici, tu mets à jour les champs que tu souhaites
        existingValidation.setMoyenneValide(validerDossier.isMoyenneValide());
        existingValidation.setDiplomeValide(validerDossier.isDiplomeValide());
        existingValidation.setPreInscrit(validerDossier.isPreInscrit());
        existingValidation.setDocumentCertifier(validerDossier.isDocumentCertifier());
        existingValidation.setDossierComple(validerDossier.isDossierComple());
        existingValidation.setFraisRegle(validerDossier.isFraisRegle());
        existingValidation.setIdentiteConfirmee(validerDossier.isIdentiteConfirmee());
       
        
        return validerDossierRepository.save(existingValidation);
    }

    public void deleteValidation(UUID id) {

        validerDossierRepository.deleteById(id);
    }

    public List<ValiderDossier> getValidationByDossier(List<DossierCandidat> candidats) {
        return validerDossierRepository.findAll();
    }
}
