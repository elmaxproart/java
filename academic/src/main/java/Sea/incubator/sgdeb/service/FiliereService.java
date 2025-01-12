package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.dto.FiliereDTO;
import Sea.incubator.sgdeb.model.Departement;
import Sea.incubator.sgdeb.model.enumType.Faculte;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Filiere service.
 */
@Service
public interface FiliereService {
    /**
     * Create filiere filiere dto.
     *
     * @param filiereDTO    info's of filiere
     * @param department_id of department
     * @return the saved filiere
     */
    FiliereDTO createFiliere(FiliereDTO filiereDTO, UUID department_id);

    /**
     * Gets all filiere by department.
     *
     * @param id of department
     * @return the list of filiere by department
     */
    List<FiliereDTO> getAllFiliereByDepartment(UUID id);
    List<FiliereDTO> getAllAsMarkedNotDeleted(UUID id);
    /**
     * Gets all filiere.
     *
     * @return all filiere of university
     */
    List<FiliereDTO> getAllFiliere();

    /**
     * Gets filiere.
     *
     * @param code the code of filiere
     * @return the found filiere
     */
    FiliereDTO getFiliere(String code);

    /**
     * Gets filiere.
     *
     * @param id of filiere
     * @return the found filiere
     */
    FiliereDTO getFiliere(UUID id);

    /**
     * Update filiere filiere dto.
     *
     * @param filiereDTO the new filiere (the information)
     * @param id         of filiere to change
     * @return the new filiere
     */
    FiliereDTO updateFiliere(FiliereDTO filiereDTO,UUID id);

    /**
     * Update name filiere dto.
     *
     * @param filiereDTO the new name
     * @param id         of filiere to change
     * @return the new filiere
     */
    FiliereDTO updateName(FiliereDTO filiereDTO, UUID id);

    /**
     * Gets filiere by libelle.
     *
     * @param libelle the libelle
     * @return the found libelle
     */
    FiliereDTO getFiliereByLibelle(String libelle);

    /**
     * Update code filiere dto.
     *
     * @param filiereDTO of filiere
     * @param id         of filiere to change
     * @return the new filiere
     */
    FiliereDTO updateCode(FiliereDTO filiereDTO, UUID id);

    /**
     * Delete filiere boolean.
     *
     * @param id of filiere to delete
     * @return yes if operation is success and no otherwise
     */
    boolean deleteFiliere(UUID id);
     void markAsDeleteFilieres(Departement departement);

     List<FiliereDTO> getAllByFaculte(String faculte);

     void updateEffectif(FiliereDTO filiereDTO,String code);
}
