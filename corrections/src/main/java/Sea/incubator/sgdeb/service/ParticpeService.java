package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.external.NoteDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The interface Particpe service.
 */
@Service
public interface ParticpeService {
    /**
     * Gets participe.
     *
     * @param participe_id the participe id
     * @return the participe found
     */
    NoteDTO getParticipe(UUID participe_id);

    /**
     * Update nom participe dto.
     *
     * @param participeDTO the participe dto
     * @param participe_id the participe id
     * @return the participe dto
     */
    NoteDTO updateNom(NoteDTO participeDTO, UUID participe_id);

    /**
     * Update matricule participe dto.
     *
     * @param participeDTO the participe dto
     * @param participe_id the participe id
     * @return the participe dto
     */
    NoteDTO updateMatricule(NoteDTO participeDTO, UUID participe_id);

    /**
     * Update note participe dto.
     *
     * @param participeDTO the participe dto
     * @param participe_id the participe id
     * @return the participe dto
     */
    NoteDTO updateNote(NoteDTO participeDTO, UUID participe_id);
}
