package Sea.incubator.sgdeb.tools;

import Sea.incubator.sgdeb.dto.GrilleExamenDTO;

import Sea.incubator.sgdeb.dto.NoteDTO;
import Sea.incubator.sgdeb.model.GrilleExamen;
import Sea.incubator.sgdeb.model.Note;
import org.springframework.stereotype.Service;

@Service
public class DTO {


    public static GrilleExamenDTO toDtoExamen(GrilleExamen grilleExamen) {
        GrilleExamenDTO grilleExamenDto = new GrilleExamenDTO();
        grilleExamenDto.setId(grilleExamen.getId());
        grilleExamenDto.setTypeEval(grilleExamen.getTypeEval());
        grilleExamenDto.setNoteSur(grilleExamen.getNoteSur());
        grilleExamenDto.setStatut(grilleExamen.getStatut());
        grilleExamenDto.setSession(grilleExamen.getSession());
        grilleExamenDto.setSemestre(grilleExamen.getSemestre());
        grilleExamenDto.setCredit(grilleExamen.getCredit());


        return grilleExamenDto;
    }

    public GrilleExamen toEntityExamen(GrilleExamenDTO grilleExamenDTO) {
        GrilleExamen grilleExamen = new GrilleExamen();
        grilleExamen.setId(grilleExamenDTO.getId());
        grilleExamen.setTypeEval(grilleExamenDTO.getTypeEval());
        grilleExamen.setNoteSur(grilleExamenDTO.getNoteSur());
        grilleExamen.setStatut(grilleExamenDTO.getStatut());
        grilleExamen.setSession(grilleExamenDTO.getSession());
        grilleExamen.setSemestre(grilleExamenDTO.getSemestre());
        grilleExamen.setCredit(grilleExamenDTO.getCredit());

        return grilleExamen;
    }

    public static NoteDTO toDtoParticipe(Note note) {
        NoteDTO noteDto = new NoteDTO();
        noteDto.setSession(note.getSession());
        noteDto.setSemestre(note.getSemestre());
        noteDto.setCredit(note.getCredit());
        noteDto.setId(note.getId());
        noteDto.setNotes(note.getNotes());
        noteDto.setNom(note.getNom());
        noteDto.setMatricule(note.getMatricule());
        noteDto.setAnonimat(note.getAnonimat());
        noteDto.setTypeEval(note.getTypeEval());
        noteDto.setNiveau(note.getNiveau());
        noteDto.setExamenNoteSur(note.getExamenNoteSur());
        noteDto.setIdGrilleExamenDTO(noteDto.getIdGrilleExamenDTO());
        noteDto.setFiliere(note.getFiliere());
        noteDto.setUE(note.getUE());
        noteDto.setAnneAcademique(note.getAnneAcademique());

        return noteDto;
    }

    public Note toEntityParticipe(NoteDTO noteDTO) {
        Note note = new Note();
        note.setId(noteDTO.getId());

        note.setNotes(noteDTO.getNotes());
        note.setNom(noteDTO.getNom());
        note.setMatricule(noteDTO.getMatricule());
        note.setAnonimat(noteDTO.getAnonimat());
        note.setTypeEval(noteDTO.getTypeEval());
        note.setNiveau(noteDTO.getNiveau());
        note.setIdGrilleExamen(note.getIdGrilleExamen());
        note.setUE(noteDTO.getUE());
        note.setExamenNoteSur(noteDTO.getExamenNoteSur());
        note.setFiliere(noteDTO.getFiliere());
        note.setSession(noteDTO.getSession());
        note.setAnneAcademique(noteDTO.getAnneAcademique());
        note.setSemestre(noteDTO.getSemestre());
        note.setCredit(noteDTO.getCredit());

        return note;
    }


}
