package Sea.incubator.sgdeb.service.impl;
import Sea.incubator.sgdeb.dto.PayementPDTO;
import Sea.incubator.sgdeb.external.Etudiant;
import Sea.incubator.sgdeb.model.PayementP;
import Sea.incubator.sgdeb.repository.PayementRepository_Preinscription;
import Sea.incubator.sgdeb.service.PayementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class PayementServiceImpl_Prescription implements PayementService {

    PayementRepository_Preinscription payementRepositoryPreinscription;
    PayementP payement;
    @Override
    public PayementP verificationPayementPrescription(String codeBank) {
        if (codeBank.isEmpty()) {
            throw new IllegalArgumentException("Le code de paiement est absent");
        }
        return payementRepositoryPreinscription.findByCodeBank(codeBank)
            .orElseThrow(()->new RuntimeException("le code de banque est introuvable"));
    }





    public PayementP createPayement(PayementPDTO payementPDTO) {

        PayementP payementP = toDTO(payementPDTO);
        return payementRepositoryPreinscription.save(payementP);
    }
    public List<PayementP> getAllPayement() {
        return payementRepositoryPreinscription.findAll();
    }

    public PayementP getPayementbyID(long id) {
        return payementRepositoryPreinscription.findById(id).orElse(null);
    }

    public PayementP updatePayement(long id, PayementPDTO payement) {
        PayementP payementToUpdate = payementRepositoryPreinscription.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé pour l'ID : " + id));

        payementToUpdate.setCodeBank(payement.getCodeBank());
        payementToUpdate.setBanque(payement.getBanque());
        payementToUpdate.setMontant(payement.getMontant());

        payementToUpdate.setNomEtudiant(payement.getNomEtudiant());
        payementToUpdate.setDatecreation(payement.getDatecreation());
        payementToUpdate.setAnneeAcademique(payement.getAnneeAcademique());
        payementToUpdate.setNiveau(payement.getNiveau());

        PayementP p=toDTO(payement);


        return payementRepositoryPreinscription.save(payementToUpdate);
    }

    public void  delectPayement(long id) {

            if (!payementRepositoryPreinscription.existsById(id)){
                throw new RuntimeException("n'exite pas");
            }
          payementRepositoryPreinscription.deleteById(id);
    }




    //dto to entity ,and entity to dto
    @Override
    public PayementP toDTO(PayementPDTO payementPDTO) {
        PayementP payementP = new PayementP();
        payementP.setPayementId(payementPDTO.getPayementId());
        payementP.setCodeBank(payementPDTO.getCodeBank());
        payementP.setBanque(payementPDTO.getBanque());
        payementP.setMontant(payementPDTO.getMontant());
        payementP.setCodePaiement(payementPDTO.getCodePaiement());

        payementP.setNomEtudiant(payementPDTO.getNomEtudiant());
        payementP.setDatecreation(payementPDTO.getDatecreation());
        payementP.setAnneeAcademique(payementPDTO.getAnneeAcademique());
        payementP.setNiveau(payementPDTO.getNiveau());
        Etudiant etudiant = new Etudiant();

        return payementP;

    }

    @Override
    public PayementPDTO toEntity(PayementP payementP) {
        PayementPDTO payementPDTO = new PayementPDTO();
        payementPDTO.setPayementId(payementP.getPayementId());
        payementPDTO.setCodeBank(payementP.getCodeBank());
        payementPDTO.setBanque(payementP.getBanque());
        payementPDTO.setMontant(payementP.getMontant());

        payementPDTO.setNomEtudiant(payementP.getNomEtudiant());
        payementPDTO.setDatecreation(payementP.getDatecreation());
        payementPDTO.setAnneeAcademique(payementP.getAnneeAcademique());
        payementPDTO.setNiveau(payementP.getNiveau());
        Etudiant etudiant = new Etudiant();
        payementPDTO.setEtudiant(etudiant);
        return payementPDTO;
    }
}
