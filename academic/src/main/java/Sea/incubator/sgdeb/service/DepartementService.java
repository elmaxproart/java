package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.dto.DepartementDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Departement service.
 */
@Service
public interface DepartementService {
    /**
     * Gets departement list.
     *
     * @return list of all departement
     */
    List<DepartementDTO> getDepartementList();
    List<DepartementDTO> getDapartmentListMarkedAsNotDeleted();
    /**
     * Gets departement.
     *
     * @param id of department
     * @return the found department
     */
    DepartementDTO getDepartement(UUID id);

    /**
     * Gets departement.
     *
     * @param nomD the name of department
     * @return the found department
     */
    DepartementDTO getDepartement(String nomD);

    /**
     * Create departement departement dto.
     *
     * @param departementDTO the info's department
     * @return the department create
     */
    DepartementDTO createDepartement(DepartementDTO departementDTO);

    /**
     * Update departement departement dto.
     *
     * @param newDepartement the new information
     * @param id             of the department to change
     * @return the department up to date
     */
    DepartementDTO updateDepartement(DepartementDTO newDepartement, UUID id);

    /**
     * Delete departement boolean.
     *
     * @param id of the department to delete
     * @return true if deleting was successful and false otherwise
     */
    boolean deleteDepartement(UUID id);
}
