package Sea.incubator.sgdeb.mapper;

import Sea.incubator.sgdeb.dto.CorrectionDto;
import Sea.incubator.sgdeb.model.Correction;
import org.springframework.stereotype.Component;

@Component
public class CorrectionMapper {
    // Convertir l'entité en DTO
    public CorrectionDto convertToDto(Correction correction) {
        if (correction == null) {
            return null;
        }

        CorrectionDto dto = new CorrectionDto();
        dto.setId(correction.getId());
        dto.setNote(correction.getNote());
        dto.setMatricule(correction.getMatricule());
        dto.setNom(correction.getNom());
        dto.setAnonymat(correction.getAnonymat());
        dto.setStatus(correction.isStatus());
        dto.setNature(correction.getNature());
        dto.setUe(correction.getUe());
        dto.setSemestre(correction.getSemestre());
        return dto;
    }

    // Convertir le DTO en entité
    public Correction convertToEntity(CorrectionDto dto) {
        if (dto == null) {
            return null;
        }

        Correction correction = new Correction();
        correction.setId(dto.getId());
        correction.setNote(dto.getNote());
        correction.setMatricule(dto.getMatricule());
        correction.setNom(dto.getNom());
        correction.setAnonymat(dto.getAnonymat());
        correction.setStatus(dto.isStatus());
        correction.setNature(dto.getNature());
        correction.setSemestre(dto.getSemestre());
        correction.setUe(dto.getUe());
        return correction;
    }
}
