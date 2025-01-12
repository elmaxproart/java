package Sea.incubator.sgdeb.service.tools;

import Sea.incubator.sgdeb.dto.DossierDTO;
import Sea.incubator.sgdeb.model.DossierCandidat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class DTO implements DossierConverter{
    @Override
    public DossierDTO toDTO(DossierCandidat dossierCandidatEntity) {
        if (dossierCandidatEntity == null) {
            return null;
        }

        DossierDTO dto = new DossierDTO();
        dto.setIdDossier(dossierCandidatEntity.getIdDossier());
        dto.setCodePaiement(dossierCandidatEntity.getCodePaiement());
        dto.setCodePreinscription(dossierCandidatEntity.getCodePreinscription());
        dto.setNomEtPrenom(dossierCandidatEntity.getNomEtPrenom());
        dto.setSexeEtudiant(dossierCandidatEntity.getSexeEtudiant());
        dto.setDateNaissance(dossierCandidatEntity.getDateNaissance());
        dto.setAge(dossierCandidatEntity.getAge());
        dto.setLieuxNaissance(dossierCandidatEntity.getLieuxNaissance());
        dto.setTelePhone(dossierCandidatEntity.getTelePhone());
        dto.setEmail(dossierCandidatEntity.getEmail());
        dto.setAdresse(dossierCandidatEntity.getAdresse());
        dto.setStatutMatrimonial(dossierCandidatEntity.getStatutMatrimonial());
        dto.setStatutProfessionnel(dossierCandidatEntity.getStatutProfessionnel());
        dto.setPremiereLangue(dossierCandidatEntity.getPremiereLangue());
        dto.setNumeroCNI(dossierCandidatEntity.getNumeroCNI());
        dto.setRegionOrigine(dossierCandidatEntity.getRegionOrigine());
        dto.setDepartement(dossierCandidatEntity.getDepartement());
        dto.setNationalite(dossierCandidatEntity.getNationalite());
        dto.setNomDuPere(dossierCandidatEntity.getNomDuPere());
        dto.setProfessionPere(dossierCandidatEntity.getProfessionPere());
        dto.setNomDeLaMere(dossierCandidatEntity.getNomDeLaMere());
        dto.setProfessionMere(dossierCandidatEntity.getProfessionMere());
        dto.setNomContact(dossierCandidatEntity.getNomContact());
        dto.setTelContact(dossierCandidatEntity.getTelContact());
        dto.setVilleContact(dossierCandidatEntity.getVilleContact());
        dto.setFaculte(dossierCandidatEntity.getFaculte());
        dto.setNiveau(dossierCandidatEntity.getNiveau());
        dto.setStatut(dossierCandidatEntity.getStatut());
        dto.setChoix(dossierCandidatEntity.getChoix());
        dto.setNomDiplome(dossierCandidatEntity.getNomDiplome());
        dto.setTypeDiplome(dossierCandidatEntity.getTypeDiplome());
        dto.setSerieObtention(dossierCandidatEntity.getSerieObtention());
        dto.setDateObtention(dossierCandidatEntity.getDateObtention());
        dto.setMoyenneObtention(dossierCandidatEntity.getMoyenneObtention());
        dto.setObservationJuridiqueMention(dossierCandidatEntity.getObservationJuridiqueMention());
        dto.setEmetteur(dossierCandidatEntity.getEmetteur());
        dto.setDateDelivrance(dossierCandidatEntity.getDateDelivrance());
        dto.setNumeroTransaction(dossierCandidatEntity.getNumeroTransaction());
        dto.setAgence(dossierCandidatEntity.getAgence());
        dto.setFraisPreinscription(dossierCandidatEntity.getFraisPreinscription());
        dto.setPratiqueSport(dossierCandidatEntity.getPratiqueSport());
        dto.setPratiqueArt(dossierCandidatEntity.getPratiqueArt());
        dto.setNumeroCertificatMedical(dossierCandidatEntity.getNumeroCertificatMedical());
        dto.setLieuxDuCertificat(dossierCandidatEntity.getLieuxDuCertificat());

        return dto;
    }

    @Override
    public DossierCandidat toEntity(DossierDTO dossierDTO) {
        if (dossierDTO == null) {
            return null;
        }

        DossierCandidat dossierCandidat = new DossierCandidat();
        dossierCandidat.setIdDossier(dossierDTO.getIdDossier());
        dossierCandidat.setCodePaiement(dossierDTO.getCodePaiement());
        dossierCandidat.setCodePreinscription(dossierDTO.getCodePreinscription());
        dossierCandidat.setNomEtPrenom(dossierDTO.getNomEtPrenom());
        dossierCandidat.setSexeEtudiant(dossierDTO.getSexeEtudiant());
        dossierCandidat.setDateNaissance(dossierDTO.getDateNaissance());
        dossierCandidat.setAge(dossierDTO.getAge());
        dossierCandidat.setLieuxNaissance(dossierDTO.getLieuxNaissance());
        dossierCandidat.setTelePhone(dossierDTO.getTelePhone());
        dossierCandidat.setEmail(dossierDTO.getEmail());
        dossierCandidat.setAdresse(dossierDTO.getAdresse());
        dossierCandidat.setStatutMatrimonial(dossierDTO.getStatutMatrimonial());
        dossierCandidat.setStatutProfessionnel(dossierDTO.getStatutProfessionnel());
        dossierCandidat.setPremiereLangue(dossierDTO.getPremiereLangue());
        dossierCandidat.setNumeroCNI(dossierDTO.getNumeroCNI());
        dossierCandidat.setRegionOrigine(dossierDTO.getRegionOrigine());
        dossierCandidat.setDepartement(dossierDTO.getDepartement());
        dossierCandidat.setNationalite(dossierDTO.getNationalite());
        dossierCandidat.setNomDuPere(dossierDTO.getNomDuPere());
        dossierCandidat.setProfessionPere(dossierDTO.getProfessionPere());
        dossierCandidat.setNomDeLaMere(dossierDTO.getNomDeLaMere());
        dossierCandidat.setProfessionMere(dossierDTO.getProfessionMere());
        dossierCandidat.setNomContact(dossierDTO.getNomContact());
        dossierCandidat.setTelContact(dossierDTO.getTelContact());
        dossierCandidat.setVilleContact(dossierDTO.getVilleContact());
        dossierCandidat.setFaculte(dossierDTO.getFaculte());
        dossierCandidat.setNiveau(dossierDTO.getNiveau());
        dossierCandidat.setStatut(dossierDTO.getStatut());
        dossierCandidat.setChoix(dossierDTO.getChoix());
        dossierCandidat.setNomDiplome(dossierDTO.getNomDiplome());
        dossierCandidat.setTypeDiplome(dossierDTO.getTypeDiplome());
        dossierCandidat.setSerieObtention(dossierDTO.getSerieObtention());
        dossierCandidat.setDateObtention(dossierDTO.getDateObtention());
        dossierCandidat.setMoyenneObtention(dossierDTO.getMoyenneObtention());
        dossierCandidat.setObservationJuridiqueMention(dossierDTO.getObservationJuridiqueMention());
        dossierCandidat.setEmetteur(dossierDTO.getEmetteur());
        dossierCandidat.setDateDelivrance(dossierDTO.getDateDelivrance());
        dossierCandidat.setNumeroTransaction(dossierDTO.getNumeroTransaction());
        dossierCandidat.setAgence(dossierDTO.getAgence());
        dossierCandidat.setFraisPreinscription(dossierDTO.getFraisPreinscription());
        dossierCandidat.setPratiqueSport(dossierDTO.getPratiqueSport());
        dossierCandidat.setPratiqueArt(dossierDTO.getPratiqueArt());
        dossierCandidat.setNumeroCertificatMedical(dossierDTO.getNumeroCertificatMedical());
        dossierCandidat.setLieuxDuCertificat(dossierDTO.getLieuxDuCertificat());

        return dossierCandidat;
    }

    @Override
    public List<DossierDTO> toDTOs(List<DossierCandidat> dossierCandidats) {
        if (dossierCandidats == null) {
            return List.of();
        }

        return dossierCandidats.stream()
                .map(this::toDTO) // Utiliser la méthode toDTO pour chaque dossier
                .collect(Collectors.toList()); // Collecter les résultats dans une liste
    }

}
