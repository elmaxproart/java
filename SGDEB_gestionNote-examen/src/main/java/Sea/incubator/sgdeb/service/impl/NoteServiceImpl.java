package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.model.GrilleExamen;
import Sea.incubator.sgdeb.model.Note;
import Sea.incubator.sgdeb.repository.GrilleExamenRepository;
import Sea.incubator.sgdeb.repository.NoteRepository;
import Sea.incubator.sgdeb.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final GrilleExamenRepository grilleExamenRepository;
    private final RestTemplate restTemplate;


    @Override
    public Note createParticipe(Note participantEntity, UUID idExamen) {
        if (idExamen == null) {
            throw new IllegalArgumentException("L'ID de l'examen ne peut pas être null.");
        }
        GrilleExamen examen =grilleExamenRepository.findById(idExamen).orElse(null);
        Optional<GrilleExamen> p = this.grilleExamenRepository.findById(idExamen);
        if (p.isEmpty()) {
            throw new RuntimeException("examen non trouver :verifier ca conformite");
        }

        Long newAnonimat = 1L;


        Optional<Note> existingNote = noteRepository.findByAnonimat(newAnonimat.toString());

        while (existingNote.isPresent()) {
            newAnonimat++;
            existingNote = noteRepository.findByAnonimat(newAnonimat.toString());
        }
        participantEntity.setAnonimat(newAnonimat.toString());
        participantEntity.setIdGrilleExamen(p.get());
        String ue = participantEntity.getUE();
        participantEntity.setUE(ue);

        assert examen != null;
        String filiere = examen.getCodeFiliere();
        participantEntity.setFiliere(filiere);

        String annee=examen.getAnneeAcademic();
        participantEntity.setAnneAcademique(annee);

        String session =examen.getSession();
        participantEntity.setSession(examen.getSession());

        int semestre =examen.getSemestre();
        participantEntity.setSemestre(semestre);

        String typeEval= String.valueOf(examen.getTypeEval());
        participantEntity.setTypeEval(typeEval);
        //credit
        double credit=examen.getCredit();
        participantEntity.setCredit(credit);

        int ExamamSur=examen.getNoteSur();
        participantEntity.setExamenNoteSur(ExamamSur);
        Optional<Note> existingNote2 = noteRepository.findByMatriculeAndAnonimat(participantEntity.getMatricule(), participantEntity.getAnonimat());

        //note update this grade skips it
        if (existingNote2.isEmpty()) {

            return noteRepository.save(participantEntity);
        }
        return null;
    }


    @Override
    public Note updateParticipe(UUID id, Note participantEntity) {
        Note existingParticipant = noteRepository.findById(id).orElse(null);
        if (existingParticipant != null) {
            participantEntity.setId(existingParticipant.getId());
            return noteRepository.save(participantEntity);
        }
        return null;
    }

    @Override
    public Note getParticipeById(UUID id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Note> getAllParticipe() {
        return noteRepository.findAll();
    }


    public List<Note> getParticipeWithId(UUID id) {
        // Fetch all notes that have the given idGrilleExamen
        return noteRepository.findAll().stream()
                .filter(note -> note.getIdGrilleExamen().getId().equals(id))
                .collect(Collectors.toList()); // Collect all matching notes into a List
    }


    @Override
    public Note updateParticipeNote(UUID id, Note participantEntity) {

        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'examen ne peut pas être null.");
        }
        Optional<Note>PretendentGrade = this.noteRepository.findById(id);

        if (PretendentGrade.isEmpty()){throw new RuntimeException("etudiant non trouver :verifier ca conformite");}
        participantEntity.setNotes(participantEntity.getNotes());
        participantEntity.setUpdateAt(LocalDateTime.now());
        participantEntity.setId(PretendentGrade.get().getId());
        participantEntity.setImportation(PretendentGrade.get().getImportation());
        participantEntity.setNom(PretendentGrade.get().getNom());
        participantEntity.setAnonimat(PretendentGrade.get().getAnonimat());
        participantEntity.setMatricule(PretendentGrade.get().getMatricule());
        participantEntity.setIdGrilleExamen(PretendentGrade.get().getIdGrilleExamen());
        participantEntity.setNiveau(PretendentGrade.get().getNiveau());
        participantEntity.setTypeEval(PretendentGrade.get().getTypeEval());
        return noteRepository.save(participantEntity);
    }
    @Override
    public Note updateParticipeNom(UUID id, Note participantEntity) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'examen ne peut pas être null.");
        }
        Optional<Note>PretendentGrade = this.noteRepository.findById(id);
        if (PretendentGrade.isEmpty()){throw new RuntimeException("etudiant non trouver :verifier ca conformite");}
        participantEntity.setNotes(PretendentGrade.get().getNotes());
        participantEntity.setUpdateAt(LocalDateTime.now());
        participantEntity.setId(PretendentGrade.get().getId());
        participantEntity.setImportation(PretendentGrade.get().getImportation());
        participantEntity.setNom(participantEntity.getNom());
        participantEntity.setAnonimat(PretendentGrade.get().getAnonimat());
        participantEntity.setMatricule(PretendentGrade.get().getMatricule());
        participantEntity.setIdGrilleExamen(PretendentGrade.get().getIdGrilleExamen());
        participantEntity.setNiveau(PretendentGrade.get().getNiveau());
        participantEntity.setTypeEval(PretendentGrade.get().getTypeEval());

      return noteRepository.save(participantEntity);

    }

    @Override
    public Note updateParticipeMatricule(UUID id, Note participantEntity) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'examen ne peut pas être null.");
        }
        Optional<Note>PretendentGrade = this.noteRepository.findById(id);
        if (PretendentGrade.isEmpty()){throw new RuntimeException("etudiant non trouver :verifier ca conformite");}
        participantEntity.setNotes(PretendentGrade.get().getNotes());
        participantEntity.setUpdateAt(LocalDateTime.now());
        participantEntity.setId(PretendentGrade.get().getId());
        participantEntity.setImportation(PretendentGrade.get().getImportation());
        participantEntity.setNom(PretendentGrade.get().getNom());
        participantEntity.setAnonimat(PretendentGrade.get().getAnonimat());
        participantEntity.setMatricule(participantEntity.getMatricule());
        participantEntity.setIdGrilleExamen(PretendentGrade.get().getIdGrilleExamen());
        participantEntity.setNiveau(PretendentGrade.get().getNiveau());
        participantEntity.setTypeEval(PretendentGrade.get().getTypeEval());
        return noteRepository.save(participantEntity);
    }

    @Override
    public void deleteParticipe(UUID id) {
        noteRepository.deleteById(id);
    }
}