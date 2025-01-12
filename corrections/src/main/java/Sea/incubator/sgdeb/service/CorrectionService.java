package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.dto.CorrectionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Correction service.
 */
@Service
public interface CorrectionService {
    /**
     * Create  correction dto.
     *
     * @param correctionDto the correction dto
     * @param examen_id     the examen id
     * @param participe_id
     * @return the correction dto
     */
    CorrectionDto createCorrection(CorrectionDto correctionDto, UUID examen_id, UUID participe_id);

    /**
     * Gets correction.
     *
     * @param correction_id the correction id
     * @return the correction
     */
    CorrectionDto getCorrection(UUID correction_id);

    /**
     * Gets correction.
     *
     * @param anonimat the anonimat
     * @return the correction
     */
    CorrectionDto getCorrection(int anonimat);

    /**
     * Update correction correction dto.
     *
     * @param correctionDto the correction dto
     * @param correction_id the correction id
     * @param participe_id
     * @return the correction dto
     */
    CorrectionDto updateCorrection(CorrectionDto correctionDto, UUID correction_id, UUID participe_id);

    /**
     * Update statut correction dto.
     *
     * @param correctionDto the correction dto
     * @param correction_id the correction id
     * @return the correction dto
     */
    CorrectionDto updateStatut(CorrectionDto correctionDto,UUID correction_id);

    /**
     * Delete correction boolean.
     *
     * @param correction_id the correction id
     * @return the boolean
     */
    boolean deleteCorrection(UUID correction_id);

    /**
     * Gets correction list.
     *
     * @return the correction list
     */
    List<CorrectionDto> getCorrectionList();
}
