package Sea.incubator.sgdeb.service;
import Sea.incubator.sgdeb.dto.AnneeAcademicDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Annee academic service.
 */
@Service
public interface AnneeAcademicService {

    /**
     * Create annee academic dto.
     *
     * @param anneeAcademicDto the annee academic dto
     * @return the annee academic dto
     */
    AnneeAcademicDto createAnneeAcademic(AnneeAcademicDto anneeAcademicDto);

    /**
     * Gets annee academic.
     *
     * @param id the id
     * @return the annee academic
     */
    AnneeAcademicDto getAnneeAcademic(UUID id);

    /**
     * Gets all annees academiques.
     *
     * @return the all annees academiques
     */
    List<AnneeAcademicDto> getAllAnneesAcademiques();
    List<AnneeAcademicDto> getAllActiveAnneeAcademic();

    /**
     * Update annee academic annee academic dto.
     *
     * @param id               the id
     * @param anneeAcademicDto the annee academic dto
     * @return the annee academic dto
     */
    AnneeAcademicDto updateAnneeAcademic(UUID id, AnneeAcademicDto anneeAcademicDto);

    /**
     * Delete annee academic boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteAnneeAcademic(UUID id);

}
