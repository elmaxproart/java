package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.GrilleExamenDTO;
import Sea.incubator.sgdeb.external.UEDTO;
import Sea.incubator.sgdeb.model.GrilleExamen;
import Sea.incubator.sgdeb.service.impl.GrilleExamenServiceImp;

import Sea.incubator.sgdeb.tools.DTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static Sea.incubator.sgdeb.tools.DTO.toDtoExamen;
@AllArgsConstructor
@RestController
@RequestMapping("api/note/examens")

public class ExamenController {

    private final DTO dto;
    private final GrilleExamenServiceImp examenServiceImp;



    @PostMapping("/add/{codeUE}")
    public ResponseEntity<String> createExamen(@RequestBody GrilleExamenDTO grilleExamenDTO, @PathVariable String codeUE) {
        GrilleExamen grilleExamenEntity = dto.toEntityExamen(grilleExamenDTO);
        GrilleExamen savedGrilleExamen = examenServiceImp.createExamen(grilleExamenEntity, codeUE);
        GrilleExamenDTO savedGrilleExamenDTO = toDtoExamen(savedGrilleExamen);
        return ResponseEntity.status(HttpStatus.CREATED).body("examen cree avec succes");
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrilleExamenDTO> updateExamen(@PathVariable UUID id, @RequestBody GrilleExamenDTO grilleExamenDTO) {
        GrilleExamen grilleExamenEntity = dto.toEntityExamen(grilleExamenDTO);
        GrilleExamen updatedGrilleExamen = examenServiceImp.updateExamen(id, grilleExamenEntity);
        if (updatedGrilleExamen == null) {
            return ResponseEntity.notFound().build();
        }
        GrilleExamenDTO updatedGrilleExamenDTO = toDtoExamen(updatedGrilleExamen);
        return ResponseEntity.ok(updatedGrilleExamenDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GrilleExamenDTO> getExamenById(@PathVariable UUID id) {
        GrilleExamen grilleExamen = examenServiceImp.getExamenById(id);
        if (grilleExamen != null) {
            GrilleExamenDTO grilleExamenDTO = toDtoExamen(grilleExamen);
            return ResponseEntity.ok(grilleExamenDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable UUID id) {
        examenServiceImp.deleteExamen(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping()
    public ResponseEntity<List<GrilleExamen>> getAllExamen() {
        List<GrilleExamen> examen = examenServiceImp.getAllExamen();
        return ResponseEntity.ok(examen);
    }

    @GetMapping("/ues/{code}")
    public ResponseEntity<UEDTO> getUeByCode(@PathVariable String code) {
        UEDTO ue = examenServiceImp.getUeByCode(code);
        if (ue != null) {
            return ResponseEntity.ok(ue);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{anneeAcademique}/{codeFiliere}/{codeUE}/{session}")
    public ResponseEntity<List<GrilleExamen>> getAllExamenWith(
            @PathVariable String anneeAcademique,
            @PathVariable String codeFiliere,
            @PathVariable String codeUE,
            @PathVariable String session
    ) {
        List<GrilleExamen> examen = examenServiceImp.getAllExamen();
        List<GrilleExamen> filteredExamList = examen.stream()
                .filter(examen1 -> examen1.getCodeUE() != null && examen1.getCodeUE().equalsIgnoreCase(codeUE))  // Filtre par code UE
                .filter(examen1 -> examen1.getAnneeAcademic() != null && examen1.getAnneeAcademic().equalsIgnoreCase(anneeAcademique))  // Vérification de null pour anneeAcademique
                .filter(examen1 -> examen1.getCodeFiliere() != null && examen1.getCodeFiliere().equalsIgnoreCase(codeFiliere))  // Vérification de null pour codeFiliere
                .filter(examen1 -> examen1.getSession() != null && examen1.getSession().equalsIgnoreCase(session))  // Vérification de null pour session
                .toList();
        return ResponseEntity.ok(filteredExamList);
    }



}
