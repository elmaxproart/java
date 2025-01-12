package Sea.incubator.sgdeb.mapper;

import Sea.incubator.sgdeb.dto.AnneeAcademicDto;
import Sea.incubator.sgdeb.model.AnneeAcademic;
import org.springframework.stereotype.Component;

/**
 * The type Annee academic mapper.
 */
@Component
public class AnneeAcademicMapper {

    /**
     * To dto annee academic dto.
     *
     * @param anneeAcademic the annee academic
     * @return the annee academic dto
     */
    public AnneeAcademicDto toDto(AnneeAcademic anneeAcademic) {
        if (anneeAcademic == null) {
            return null;
        }

        AnneeAcademicDto dto = new AnneeAcademicDto();
        dto.setId(anneeAcademic.getId());
        dto.setFin(anneeAcademic.getFin());
        dto.setDebut(anneeAcademic.getDebut());
        dto.makeLibelle(); // Met à jour le libellé

        return dto;
    }

    /**
     * To entity annee academic.
     *
     * @param dto the dto
     * @return the annee academic entity
     */
    public AnneeAcademic toEntity(AnneeAcademicDto dto) {
        if (dto == null) {
            return null;
        }

        AnneeAcademic anneeAcademic = new AnneeAcademic();
        anneeAcademic.setId(dto.getId());
        anneeAcademic.setFin(dto.getFin());
        anneeAcademic.setDebut(dto.getDebut());
        anneeAcademic.setLibelle(dto.getLibelle());

        return anneeAcademic;
    }
}
