package Sea.incubator.sgdeb.service;
import Sea.incubator.sgdeb.dto.PayementIDTO;
import Sea.incubator.sgdeb.model.PayementI;


public interface PayementServiceI {
    PayementI verificationPayementInscription(String  codeBank);
    PayementI toDTO(PayementIDTO payementIDTO);
    PayementIDTO toEntity(PayementI paymentI);
}
