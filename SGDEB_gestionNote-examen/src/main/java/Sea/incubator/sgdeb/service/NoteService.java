package Sea.incubator.sgdeb.service;


import Sea.incubator.sgdeb.model.Note;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    Note createParticipe(Note participantEntity, UUID idExamen);

    Note updateParticipe(UUID id, Note participantEntity);

    Note getParticipeById(UUID id);

    List<Note> getAllParticipe();


    Note updateParticipeNote(UUID id, Note participantEntity);

    Note updateParticipeNom(UUID id, Note participantEntity);

    Note updateParticipeMatricule(UUID id, Note participantEntity);

    void deleteParticipe(UUID id);
}
