package Sea.incubator.sgdeb.mapper;



import Sea.incubator.sgdeb.dto.FiliereDTO;
import Sea.incubator.sgdeb.model.Filiere;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Filiere mapper.
 */
@Component
public class FiliereMapper{
    /**
     * To dto filiere dto.
     *
     * @param filiere the filiere
     * @return the filiere dto
     */
    public FiliereDTO toDto(Filiere filiere){
        FiliereDTO filiereDTO=new FiliereDTO();
        filiereDTO.setCode(filiere.getCode());
        filiereDTO.setLibelle(filiere.getLibelle());
        filiereDTO.setDepartement(DepartementMapper.departementToDepartementDTO(filiere.getDepartement()));
        filiereDTO.setId(filiere.getId());
        filiereDTO.setFaculte(filiere.getFaculte());
        filiereDTO.setEffectif(filiereDTO.getEffectif());
        return  filiereDTO;
    }

    /**
     * To entity filiere.
     *
     * @param filiereDTO the filiere dto
     * @return the filiere
     */
    public static Filiere toEntity(FiliereDTO filiereDTO){
        Filiere filiere=new Filiere();
        filiere.setCode(filiereDTO.getCode());
        filiere.setLibelle(filiereDTO.getLibelle());
        filiere.setDepartement(DepartementMapper.departementToDepartementDTO(filiereDTO.getDepartement()));
        filiere.setId(filiereDTO.getId());
        filiere.setFaculte(filiereDTO.getFaculte());
        filiere.setEffectif(filiere.getEffectif());
        return filiere;
    }

}