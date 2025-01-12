package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.dto.ECDTO;
import Sea.incubator.sgdeb.model.UE;

import java.util.List;
import java.util.UUID;

/**
 * The interface Ec service.
 */
public interface ECService {

    /**
     * Create ec ecdto.
     *
     * @param ecDTO the ec dto
     * @param ueId  the ue id
     * @return the ecdto
     */
    ECDTO createEc(ECDTO ecDTO, UUID ueId);

    /**
     * Gets ec.
     *
     * @param ecId the ec id
     * @return the ec
     */
    ECDTO getEc(UUID ecId);

    /**
     * Gets ec.
     *
     * @param codeEc the code ec
     * @return the ec
     */
    ECDTO getEc(String codeEc);

    /**
     * Update ec ecdto.
     *
     * @param ecDTO the ec dto
     * @param ecId  the ec id
     * @return the ecdto
     */
    ECDTO updateEc(ECDTO ecDTO, UUID ecId);

    /**
     * Update nom ec ecdto.
     *
     * @param ecDTO the ec dto
     * @param ecId  the ec id
     * @return the ecdto
     */
    ECDTO updateNomEc(ECDTO ecDTO, UUID ecId);

    /**
     * Update code ecdto.
     *
     * @param ecDTO the ec dto
     * @param ecId  the ec id
     * @return the ecdto
     */
    ECDTO updateCode(ECDTO ecDTO, UUID ecId);

    /**
     * Delete ec boolean.
     *
     * @param ecId the ec id
     * @return the boolean
     */
    boolean deleteEc(UUID ecId);

    /**
     * Gets all by ue.
     *
     * @param ueId the ue id
     * @return the all by ue
     */
    List<ECDTO> getAllByUE(UUID ueId);
    List<ECDTO> getAllActiveByUE(UUID ueId);
    void markAsDeletedEc(UE ue);
}
