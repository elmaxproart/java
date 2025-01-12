package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.external.NoteDTO;
import Sea.incubator.sgdeb.service.ParticpeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * The type Participe service.
 */
@Service
@AllArgsConstructor
public class ParticipeServiceImpl implements ParticpeService {
    private final RestTemplate restTemplate;

    @Override
    public NoteDTO getParticipe(UUID participe_id) {
        String url = "http://localhost:8090/api/note/saisie/" + participe_id;

        // Utiliser getForObject pour récupérer l'objet
        ResponseEntity<NoteDTO> response = restTemplate.getForEntity(url, NoteDTO.class);
        return response.getBody();
    }

    @Override
    public NoteDTO updateNom(NoteDTO participeDTO, UUID participe_id) {
        String url = "http://localhost:8090/api/note/saisie/updateNom/" + participe_id;

        // Créer l'entité HTTP avec le corps de la requête
        HttpEntity<NoteDTO> requestEntity = new HttpEntity<>(participeDTO);

        // Effectuer la requête PATCH
        ResponseEntity<NoteDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, NoteDTO.class);
        return response.getBody();
    }

    @Override
    public NoteDTO updateMatricule(NoteDTO participeDTO, UUID participe_id) {
        String url = "http://localhost:8090/api/note/saisie/updateMatricule/" + participe_id;

        // Créer l'entité HTTP avec le corps de la requête
        HttpEntity<NoteDTO> requestEntity = new HttpEntity<>(participeDTO);

        // Effectuer la requête PATCH
        ResponseEntity<NoteDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, NoteDTO.class);
        return response.getBody();
    }

    @Override
    public NoteDTO updateNote(NoteDTO participeDTO, UUID participe_id) {
        String url = "http://localhost:8090/api/note/saisie/updateNote/" + participe_id;

        // Créer l'entité HTTP avec le corps de la requête
        HttpEntity<NoteDTO> requestEntity = new HttpEntity<>(participeDTO);

        // Effectuer la requête PATCH
        ResponseEntity<NoteDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, NoteDTO.class);
        return response.getBody();
    }
}
