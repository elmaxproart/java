package Sea.incubator.sgdeb.controller;
import Sea.incubator.sgdeb.dto.NoteDTO;

import Sea.incubator.sgdeb.model.Note;
import Sea.incubator.sgdeb.service.impl.NoteServiceImpl;
import Sea.incubator.sgdeb.tools.DTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/note/saisie")
public class SaisieNotesController {

    private  final DTO dto;
   private final NoteServiceImpl participantServiceImp;
    private final RestTemplate restTemplate;



    @PostMapping("/add/{idExamen}")
   public ResponseEntity<NoteDTO> createParticipe(@RequestBody NoteDTO participantDTO, @PathVariable UUID idExamen) {
       Note participantEntity = dto.toEntityParticipe(participantDTO);
       Note savedParticipant = participantServiceImp.createParticipe(participantEntity,idExamen);
       NoteDTO savedParticipantDTO = DTO.toDtoParticipe(savedParticipant);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedParticipantDTO);
   }
   @PutMapping("/{id}")
   public ResponseEntity<NoteDTO> updateParticipe(@PathVariable UUID id, @RequestBody NoteDTO participantDTO) {
       Note participantEntity = dto.toEntityParticipe(participantDTO);
       Note updatedParticipant = participantServiceImp.updateParticipe(id, participantEntity);
       if (updatedParticipant == null) {
           return ResponseEntity.notFound().build();
       }
       NoteDTO updatedParticipantDTO = DTO.toDtoParticipe(updatedParticipant);
       return ResponseEntity.ok(updatedParticipantDTO);
   }
   @GetMapping("/{id}")
   public ResponseEntity<NoteDTO> getParticipeById(@PathVariable UUID id) {
       Note participant = participantServiceImp.getParticipeById(id);
       if (participant != null) {
           NoteDTO participantDTO = DTO.toDtoParticipe(participant);
           return ResponseEntity.ok(participantDTO);
       } else {
           return ResponseEntity.notFound().build();
       }
   }

   @GetMapping
   public ResponseEntity<List<NoteDTO>> getAllParticipe() {
       List<Note> participants = participantServiceImp.getAllParticipe();
       List<NoteDTO> participantDTOs = new ArrayList<>();
       for (Note participant : participants) {
           NoteDTO participantDTO = DTO.toDtoParticipe(participant);
           participantDTOs.add(participantDTO);
       }
       return ResponseEntity.ok(participantDTOs);
   }
    @PutMapping("updateMatricule/{id}")
    public ResponseEntity<NoteDTO> updateParticipeMatricule(@PathVariable UUID id, @RequestBody NoteDTO participantDTO) {
        Note participantEntity = dto.toEntityParticipe(participantDTO);
        Note updatedParticipant = participantServiceImp.updateParticipeMatricule(id, participantEntity);
        if (updatedParticipant == null) {
            return ResponseEntity.notFound().build();
        }
        NoteDTO updatedParticipantDTO = DTO.toDtoParticipe(participantEntity);
        return ResponseEntity.ok(updatedParticipantDTO);
    }
    @PutMapping("updateNote/{id}")
    public ResponseEntity<NoteDTO> updateParticipeNote(@PathVariable UUID id, @RequestBody NoteDTO participantDTO) {
        Note participantEntity = dto.toEntityParticipe(participantDTO);
        Note updatedParticipant = participantServiceImp.updateParticipeNote(id, participantEntity);
        if (updatedParticipant == null) {
            return ResponseEntity.notFound().build();
        }
        NoteDTO updatedParticipantDTO = DTO.toDtoParticipe(updatedParticipant);
        return ResponseEntity.ok(updatedParticipantDTO);
    }
    @PutMapping("updateNom/{id}")
    public ResponseEntity<NoteDTO> updateParticipeNom(@PathVariable UUID id, @RequestBody NoteDTO participantDTO) {
        Note participantEntity = dto.toEntityParticipe(participantDTO);
        Note updatedParticipe=participantServiceImp.updateParticipeNom(id, participantEntity);


        NoteDTO updatedParticipantDTO = DTO.toDtoParticipe(updatedParticipe);
        return ResponseEntity.ok(updatedParticipantDTO);
    }
    //recuperation de la note sans anonimat
    @GetMapping("/sansAnonimat")
    public ResponseEntity<List<NoteDTO>> getNoteSansAnonimat() {
        List<Note> notes = participantServiceImp.getAllParticipe();
        List<NoteDTO> noteDTOs = new ArrayList<>();
        for (Note note : notes) {
            NoteDTO noteDTO = dto.toDtoParticipe(note);
            noteDTOs.add(noteDTO);
        }
        return ResponseEntity.ok(noteDTOs);
    }
    //recuperation de la note sans anonimat
    @GetMapping("/sansAnonimat/{idExamen}")
    public ResponseEntity<List<NoteDTO>> getNoteSansAnonimatWithId(@PathVariable UUID idExamen) {
        List<Note> notes = participantServiceImp.getParticipeWithId(idExamen);
        List<NoteDTO> noteDTOs = new ArrayList<>();
        for (Note note : notes) {
            NoteDTO noteDTO = dto.toDtoParticipe(note);
            noteDTOs.add(noteDTO);
        }
        return ResponseEntity.ok(noteDTOs);
    }
    @DeleteMapping("delect/{id}")
    public ResponseEntity<Void> deleteParticipe(@PathVariable UUID id) {
        participantServiceImp.deleteParticipe(id);
        return ResponseEntity.noContent().build();
    }


}
