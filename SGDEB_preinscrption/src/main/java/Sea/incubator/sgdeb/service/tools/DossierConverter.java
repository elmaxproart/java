package Sea.incubator.sgdeb.service.tools;

import Sea.incubator.sgdeb.dto.DossierDTO;
import Sea.incubator.sgdeb.model.DossierCandidat;

import java.util.List;

public interface DossierConverter {
    DossierDTO toDTO(DossierCandidat dossierCandidatEntity);
    DossierCandidat toEntity(DossierDTO dossierDTO);
    List<DossierDTO> toDTOs(List<DossierCandidat> dossierCandidats);

}