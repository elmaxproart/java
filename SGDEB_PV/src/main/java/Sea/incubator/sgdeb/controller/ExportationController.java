package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.PvDetailsDTO;
import Sea.incubator.sgdeb.dto.PvDetailsRecapDTO;
import Sea.incubator.sgdeb.external.GrilleDTO;
import Sea.incubator.sgdeb.external.NoteDTO;
import Sea.incubator.sgdeb.model.PvRecap;
import Sea.incubator.sgdeb.model.PvUE;
import Sea.incubator.sgdeb.service.ExportationService;
import Sea.incubator.sgdeb.tools.RestTamplateAcces;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/exportation/pv/")
public class ExportationController {

    private final ExportationService exportationService;
    private final RestTamplateAcces restTamplateAcces;


    @PostMapping("/ue")
    public ResponseEntity<FileSystemResource> exportNotes(
            @RequestParam("codeUE") String codeUE,
            @RequestParam("typeEval") String typeEval
    ) throws DocumentException, IOException {


        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = "notes";
        String filePath = System.getProperty("java.io.tmpdir") + "/" + fileName + "_" + timestamp + ".pdf";
        // Export notes to PDF
        exportationService.ExportationEnPdf(filePath,codeUE,typeEval);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        FileSystemResource fileResource = new FileSystemResource(filePath);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileResource.contentLength())
                .body(fileResource);

    }

    @PostMapping("/recap")
    public ResponseEntity<FileSystemResource> exportNotesRecap(
            @RequestParam("libelleGrille") String libelleGrille

    ) throws DocumentException, IOException {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = "notes-recap";
        String filePath = System.getProperty("java.io.tmpdir") + "/" + fileName + "_" + timestamp + ".pdf";
        // Export notes to PDF
        exportationService.exportRecapToPdf(filePath, libelleGrille);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        FileSystemResource fileResource = new FileSystemResource(filePath);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileResource.contentLength())
                .body(fileResource);

    }


    @PostMapping("/loadData")
    public ResponseEntity<List<PvUE>> loadDataPv() {
        try {
            List<PvUE> importedGrade = exportationService.LoardDataToExportPv();
            return ResponseEntity.status(HttpStatus.CREATED).body(importedGrade);
        } catch (Exception e) {
            // Gérer l'erreur de manière appropriée
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/loadDataPvRecap")
    public ResponseEntity<List<PvRecap>> loadDataPvRecap() {
        try {
            List<PvRecap> importedGrade = exportationService.loadDataFromPvRecap();
            return ResponseEntity.status(HttpStatus.CREATED).body(importedGrade);
        } catch (Exception e) {
            // Gérer l'erreur de manière appropriée
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAll() {
        List<NoteDTO> participants = restTamplateAcces.getAllGradeWithAnonymat();
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/getPv")
    public ResponseEntity<List<PvUE>> getPvInformations(
            @RequestParam("codeUE") String codeUE,
            @RequestParam("typeEval") String typeEval
    ) {
        try {
            // Récupérer tous les PV
            List<PvUE> pvList = exportationService.getAll();

            // Appliquer les filtres sur codeUE et typeEval
            List<PvUE> filteredPvList = pvList.stream()
                    .filter(pv -> pv.getCodeUE().equalsIgnoreCase(codeUE))  // Filtre par code UE
                    .filter(pv -> pv.getTypeEval().equalsIgnoreCase(typeEval))  // Filtre par type d'évaluation
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(filteredPvList);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des PV", e);
        }
    }


    @GetMapping("/getPvRecap")
    public ResponseEntity<List<PvRecap>> getPvRecapInformations(
            @RequestParam("libelleGrille") String libelleGrille
    ) {
        try {
            // Récupérer la grille en utilisant le libelle
            GrilleDTO grilleDTO = restTamplateAcces.getGrille(libelleGrille);

            if (grilleDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.emptyList());
            }

            // Assurez-vous que vous récupérez les strings corrects pour le niveau, la filière et l'année académique
            String niveau = grilleDTO.getNiveau().toString();
            String codeFil = grilleDTO.getFiliere().getCode();
            String annee = grilleDTO.getAnneeAcademic().getLibelle();

            // Récupérer tous les PV Recap
            List<PvRecap> pvRecapList = exportationService.getAllRecap();

            // Appliquer les filtres basés sur les informations de la grille
            List<PvRecap> filteredPvRecapList = pvRecapList.stream()
                    .filter(pv -> pv.getNiveaux().equalsIgnoreCase(niveau))
                    .filter(pv -> pv.getCodeFil().equalsIgnoreCase(codeFil))  // Filtre par filière
                    .filter(pv -> pv.getAnnee().equalsIgnoreCase(annee))  // Filtre par année académique
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(filteredPvRecapList);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des PV Recap", e);
        }
    }



    @PutMapping
    public ResponseEntity<PvRecap>PutGrade(){
        try {
            PvRecap pv =exportationService.putGrade();
            return ResponseEntity.status(HttpStatus.OK).body(pv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/getPvDetails")
    public ResponseEntity<List<PvDetailsDTO>> getPvDetails() {
        try {
            List<PvDetailsDTO> pv = exportationService.getPvDetails();
            return ResponseEntity.status(HttpStatus.OK).body(pv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/getPvDetailsRecap")
    public ResponseEntity<List<PvDetailsRecapDTO>> getPvDetailsRecap() {
        try {
            List<PvDetailsRecapDTO> pv = exportationService.getPvDetailsRecap();
            return ResponseEntity.status(HttpStatus.OK).body(pv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/update/{matricule}")
    public ResponseEntity<PvRecap> updateStudentGrades(
            @PathVariable("matricule") String matricule){
        try {
            PvRecap updatedPvRecap = exportationService.updateStudentGrades(matricule);
            return ResponseEntity.ok(updatedPvRecap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}