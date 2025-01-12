package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.dto.PayementIDTO;
import Sea.incubator.sgdeb.external.Etudiant;
import Sea.incubator.sgdeb.model.PayementI;
import Sea.incubator.sgdeb.repository.PayementRepository_Inscription;
import Sea.incubator.sgdeb.service.PayementServiceI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class PayementServiceImpl_Inscription implements PayementServiceI {

    private final  PayementRepository_Inscription payementRepositoryInscription;

    public PayementServiceImpl_Inscription(PayementRepository_Inscription payementRepositoryInscription) {
        this.payementRepositoryInscription = payementRepositoryInscription;
    }


    @Override
    public PayementI verificationPayementInscription(String Matricule) {

        if (Matricule.isEmpty()) {
            throw new IllegalArgumentException("Le Matricule de paiement est absent");
        }
        return payementRepositoryInscription.findByMatriculeEtu(Matricule)
                .orElseThrow(()->new RuntimeException("le Matricule de banque est introuvable"));
    }

    @Override
    public PayementI toDTO(PayementIDTO payementIDTO) {

        PayementI payementI = new PayementI();
        payementI.setPayementId(payementI.getPayementId());
        payementI.setCodeBank(payementI.getCodeBank());
        payementI.setMatriculeEtu(payementI.getMatriculeEtu());
        payementI.setBanque(payementI.getBanque());
        payementI.setMontant(payementI.getMontant());
        payementI.setTypeTranche(payementI.getTypeTranche());
        payementI.setNomEtudiant(payementI.getNomEtudiant());
        payementI.setDatecreation(payementI.getDatecreation());
        payementI.setAnneeAcademique(payementI.getAnneeAcademique());
        return payementI;
    }

    @Override
    public PayementIDTO toEntity(PayementI payementI) {

        PayementIDTO payementIDTO = new PayementIDTO();
        payementIDTO.setPayementId(payementI.getPayementId());
        payementIDTO.setCodeBank(payementI.getCodeBank());
        payementIDTO.setMatriculeEtu(payementI.getMatriculeEtu());
        payementIDTO.setBanque(payementI.getBanque());
        payementIDTO.setMontant(payementI.getMontant());
        payementIDTO.setTypeTranche(payementI.getTypeTranche());
        payementIDTO.setNomEtudiant(payementI.getNomEtudiant());
        payementIDTO.setDatecreation(payementI.getDatecreation());
        payementIDTO.setAnneeAcademique(payementI.getAnneeAcademique());
        payementIDTO.setEtudiant(payementIDTO.getEtudiant());
        Etudiant etudiant = new Etudiant();
        payementIDTO.setEtudiant(etudiant);
        return payementIDTO;
    }


    public PayementI getPayementbyID(long id) {
        return payementRepositoryInscription.findById(id).orElse(null);
    }

    public PayementI updatePayement(long id, PayementI payementInscriptionDto) {
        PayementI payementToUpdate = payementRepositoryInscription.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé pour l'ID : " + id));


        payementToUpdate.setCodeBank(payementInscriptionDto.getCodeBank());
        payementToUpdate.setMatriculeEtu(payementInscriptionDto.getMatriculeEtu());
        payementToUpdate.setBanque(payementInscriptionDto.getBanque());
        payementToUpdate.setMontant(payementInscriptionDto.getMontant());
        payementToUpdate.setTypeTranche(payementInscriptionDto.getTypeTranche());
        payementToUpdate.setNomEtudiant(payementInscriptionDto.getNomEtudiant());
        payementToUpdate.setDatecreation(payementInscriptionDto.getDatecreation());
        payementToUpdate.setAnneeAcademique(payementInscriptionDto.getAnneeAcademique());

        return payementRepositoryInscription.save(payementToUpdate);
    }

    public void delectPayement(long id) {
        if (!payementRepositoryInscription.existsById(id)){
            throw new RuntimeException("n'exite pas");
        }
        payementRepositoryInscription.deleteById(id);
    }

    public PayementI createPayement(PayementI payementInscriptionDto) {

        return payementRepositoryInscription.save(payementInscriptionDto);
    }

    public List<PayementI> getAllPayement() {
        return payementRepositoryInscription.findAll();
    }

    public PayementI getPayementbycode(long code) {
        return payementRepositoryInscription.findBycodeBank(code).orElse(null);
    }
}
