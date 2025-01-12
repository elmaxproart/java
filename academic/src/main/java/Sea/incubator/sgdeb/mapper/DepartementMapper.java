package Sea.incubator.sgdeb.mapper;

import Sea.incubator.sgdeb.dto.DepartementDTO;
import Sea.incubator.sgdeb.model.Departement;
import org.springframework.stereotype.Component;


/**
 * The type Departement mapper.
 */
@Component
public class DepartementMapper {

    /**
     * Departement to departement dto departement dto.
     *
     * @param departement the departement
     * @return the departement dto
     */
    public static DepartementDTO departementToDepartementDTO(Departement departement) {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setId(departement.getId());
        departementDTO.setNomDepartment(departement.getNomDepartment());

        return departementDTO;
    }

    /**
     * Departement to departement dto departement.
     *
     * @param departementDTO the departement dto
     * @return the departement
     */
    public static Departement departementToDepartementDTO(DepartementDTO departementDTO) {
        Departement departement = new Departement();
        departement.setId(departementDTO.getId());
        departement.setNomDepartment(departementDTO.getNomDepartment());
        return departement;
    }




}
