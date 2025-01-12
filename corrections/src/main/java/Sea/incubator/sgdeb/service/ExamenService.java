package Sea.incubator.sgdeb.service;


import Sea.incubator.sgdeb.external.GrilleExamenDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The interface Examen service.
 */
@Service
public interface ExamenService {
    /**
     * Gets examen.
     *
     * @param examen_id the examen id
     * @return the examen
     */
    GrilleExamenDTO getExamen(UUID examen_id);
}
