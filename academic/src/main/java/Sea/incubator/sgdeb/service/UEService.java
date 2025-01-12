package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.dto.UEDTO;
import Sea.incubator.sgdeb.model.Grille;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Ue service.
 */
@Service
public interface UEService {


    /**
     * Gets all ue by grille.
     *
     * @param grilleId the grille id
     * @return the all ue by grille
     */
    List<UEDTO> getAllUEByGrille(UUID grilleId);
    List<UEDTO> getAllActiveUEByGrille(UUID grilleId);

    /**
     * Gets ue.
     *
     * @param id the id
     * @return the ue
     */
    UEDTO getUE(UUID id);

    /**
     * Create ue uedto.
     *
     * @param ueDTO    the ue dto
     * @param grilleId the grille id
     * @return the uedto
     */
    UEDTO createUE(UEDTO ueDTO, UUID grilleId);

    /**
     * Gets ue.
     *
     * @param code the code
     * @return the ue
     */
    UEDTO getUE(String code);

    /**
     * Update ue uedto.
     *
     * @param ueDTO the ue dto
     * @param ueId  the ue id
     * @return the uedto
     */
    UEDTO updateUE(UEDTO ueDTO, UUID ueId);

    /**
     * Update libelle uedto.
     *
     * @param ueDTO the ue dto
     * @param ueId  the ue id
     * @return the uedto
     */
    UEDTO updateLibelle(UEDTO ueDTO, UUID ueId);

    /**
     * Update code uedto.
     *
     * @param ueDTO the ue dto
     * @param ueId  the ue id
     * @return the uedto
     */
    UEDTO updateCode(UEDTO ueDTO, UUID ueId);

    /**
     * Delete ue boolean.
     *
     * @param ueId the ue id
     * @return the boolean
     */
    boolean deleteUE(UUID ueId);
    void markAsDeleteUE(Grille grille);


}
