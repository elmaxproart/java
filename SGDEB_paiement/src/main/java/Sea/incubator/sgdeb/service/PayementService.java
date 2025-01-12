package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.dto.PayementPDTO;
import Sea.incubator.sgdeb.model.PayementP;

public interface PayementService {
    PayementP verificationPayementPrescription(String codeBank);
    PayementP toDTO(PayementPDTO payementPDTO);
    PayementPDTO toEntity(PayementP paymentP);
}
