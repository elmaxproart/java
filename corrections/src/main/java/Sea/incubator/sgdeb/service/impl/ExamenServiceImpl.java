package Sea.incubator.sgdeb.service.impl;


import Sea.incubator.sgdeb.external.GrilleExamenDTO;
import Sea.incubator.sgdeb.service.ExamenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * The type Examen service.
 */
@Service
public class ExamenServiceImpl implements ExamenService {
    private final RestTemplate restTemplate;

    /**
     * Instantiates a new Examen service.
     *
     * @param restTemplate the rest template
     */
    public ExamenServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GrilleExamenDTO getExamen(UUID examenId) {
        String url = "http://localhost:8090/api/note/examens/" + examenId;
        ResponseEntity<GrilleExamenDTO> responseEntity = restTemplate.getForEntity(url, GrilleExamenDTO.class);
        return responseEntity.getBody();

    }
}
