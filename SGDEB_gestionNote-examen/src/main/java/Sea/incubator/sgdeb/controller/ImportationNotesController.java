package Sea.incubator.sgdeb.controller;


import Sea.incubator.sgdeb.external.UEDTO;
import Sea.incubator.sgdeb.external.enumType.Semestre;
import Sea.incubator.sgdeb.external.enumType.TypeEval;
import Sea.incubator.sgdeb.model.GrilleExamen;
import Sea.incubator.sgdeb.model.Importation;
import Sea.incubator.sgdeb.model.Note;
import Sea.incubator.sgdeb.repository.GrilleExamenRepository;
import Sea.incubator.sgdeb.service.ImportationService;
import Sea.incubator.sgdeb.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/note/import")
@AllArgsConstructor

public class ImportationNotesController {

    private final ImportationService importationService;
    private final GrilleExamenRepository examenRepository;


    private final NotificationService notificationService;





    @PostMapping("/import-noteByExam")
    public ResponseEntity<String> importNotesFromExcel(@RequestParam("file") MultipartFile file ,@RequestParam("idExamen") UUID idExamen) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Veuillez fournir un fichier Excel.");
        }


            List<Note> importedNotes = importationService.importationFromExel(file,idExamen);
        if (importedNotes != null) {
            return ResponseEntity.ok("Les notes ont été importées avec succès depuis le fichier Excel.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'importation des notes depuis le fichier Excel.");
        }
    }

    @PostMapping("/import-notes")
    public ResponseEntity<String> importCradesFromExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("codeUE")String codeUE,
            @RequestParam("codeFiliere")String codeFiliere,
            @RequestParam("examen") String typeEvalstr,
            @RequestParam("noteSur")int noteSur,
            @RequestParam("session")String session

    ) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Veuillez fournir un fichier Excel.");
        }
        UEDTO uedto;
        try {
            uedto = importationService.getUEByCode(codeUE.toUpperCase());
            if (uedto == null) {
                return ResponseEntity.badRequest().body("L'UE avec le code fourni n'existe pas.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la vérification de l'UE: " + e.getMessage());
        }
        TypeEval typeEvals;
        try {
            typeEvals = TypeEval.valueOf(typeEvalstr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Le type d'évaluation est incorrect. Utilisez CC, EE ou EP.");
        }
        GrilleExamen examen = new GrilleExamen();
        examen.setCodeUE(codeUE.toUpperCase());
        examen.setCodeFiliere(codeFiliere.toUpperCase());
        examen.setTypeEval(typeEvals);
        examen.setNoteSur(noteSur);
        examen.setSession(session.toUpperCase());
        examen.setSemestre(uedto.getSemestre() == Semestre.S1 ? 1 : (uedto.getSemestre() == Semestre.S2 ? 2 : 3));
        examen.setCredit(uedto.getCredits());
        examen.setAnneeAcademic(uedto.getGrille().getAnneeAcademic().getLibelle());
        // Vérifier si un examen avec les mêmes paramètres existe déjà
        // Vérifier si un examen avec les mêmes paramètres existe déjà
        try {
            GrilleExamen existingExamen = examenRepository.findByCodeUEAndCodeFiliereAndTypeEvalAndNoteSur(
                    codeUE, codeFiliere,typeEvals, noteSur);
            if (existingExamen != null) {
                return ResponseEntity.badRequest().body("Cet examen existe déjà.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la vérification de l'existence de l'examen : " + e.getMessage());
        }
        try {
            // Persist the examen entity
            GrilleExamen savedExamen = examenRepository.save(examen);

            // Import notes from the Excel file using the ID of the saved examen
            List<Note> importedNotes = importationService.importationFromExel(file, savedExamen.getId());
            if (importedNotes != null) {
                // Envoyer une notification via WebSocket
                notificationService.sendNotification("Les notes pour " + codeUE + " ont été importées avec succès.");
                return ResponseEntity.ok("Les notes ont été importées avec succès depuis le fichier Excel.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'importation des notes depuis le fichier Excel.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la sauvegarde de l'examen ou de l'importation des notes : " + e.getMessage());
        }


    }

    //importation rapide
    @PostMapping("/quick-import")
    public ResponseEntity<String> importNotesFromExcel(@RequestParam("file") MultipartFile file) {
        // Vérifier si le fichier est fourni et non vide
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Veuillez fournir un fichier Excel.");
        }

        // Extraction du nom du fichier
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains("_")) {
            return ResponseEntity.badRequest().body("Le nom du fichier est mal formaté. Utilisez le format attendu.");
        }

        // Supprimer l'extension du fichier s'il y en a une
        filename = filename.replace(".xlsx", "");

        // Découper le nom du fichier en utilisant "_"
        String[] filenameParts = filename.split("_");

        // Vérification du format du nom de fichier
        if (filenameParts.length < 6) {
            return ResponseEntity.badRequest().body("Le nom du fichier doit contenir suffisamment d'informations (au moins 6 parties).");
        }

        // Récupérer les informations du fichier
        String codeUE = filenameParts[1];
        String codeFiliere = filenameParts[2];
        String typeEvalStr = filenameParts[3];
        String session = filenameParts[5];

        // Vérifier si la partie "note sur" est un nombre valide
        int noteSur;
        try {
            noteSur = Integer.parseInt(filenameParts[4]);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Le format de la note sur est incorrect. Il doit s'agir d'un entier.");
        }

        // Vérification du type d'évaluation
        TypeEval typeEval;
        try {
            typeEval = TypeEval.valueOf(typeEvalStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Le type d'évaluation est incorrect. Utilisez CC, EE ou EP.");
        }

        // Vérifier si l'UE existe dans la base de données
        UEDTO uedto;
        try {
            uedto = importationService.getUEByCode(codeUE);
            if (uedto == null) {
                return ResponseEntity.badRequest().body("L'UE avec le code fourni n'existe pas.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la vérification de l'UE: " + e.getMessage());
        }

        // Création d'un objet GrilleExamen et remplissage des informations
        GrilleExamen examen = new GrilleExamen();
        examen.setCodeUE(codeUE);
        examen.setCodeFiliere(codeFiliere);
        examen.setTypeEval(typeEval);
        examen.setNoteSur(noteSur);
        examen.setSession(session);
        examen.setSemestre(uedto.getSemestre() == Semestre.S1 ? 1 : (uedto.getSemestre() == Semestre.S2 ? 2 : 3));
        examen.setCredit(uedto.getCredits());
        examen.setAnneeAcademic(uedto.getGrille().getAnneeAcademic().getLibelle());

        // Vérifier si un examen avec les mêmes paramètres existe déjà
        try {
            GrilleExamen existingExamen = examenRepository.findByCodeUEAndCodeFiliereAndTypeEvalAndNoteSur(
                    codeUE, codeFiliere,typeEval, noteSur);
            if (existingExamen != null) {
                return ResponseEntity.badRequest().body("Cet examen existe déjà.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la vérification de l'existence de l'examen : " + e.getMessage());
        }

        // Sauvegarder l'examen dans la base de données
        try {
            examenRepository.save(examen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la sauvegarde de l'examen : " + e.getMessage());
        }

        // Importer les notes à partir du fichier Excel
        List<Note> importedNotes;
        try {
            importedNotes = importationService.importationFromExel(file, examen.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'importation des notes depuis le fichier Excel : " + e.getMessage());
        }

        // Vérifier si les notes ont été correctement importées
        if (importedNotes != null && !importedNotes.isEmpty()) {
            return ResponseEntity.ok("Les notes ont été importées avec succès depuis le fichier Excel.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Aucune note n'a été importée depuis le fichier Excel.");
        }
    }

    @GetMapping("/importDetails/{idExamen}")
    public ResponseEntity<Importation> importDetails(@PathVariable UUID idExamen) {
        return ResponseEntity.ok(importationService.importationDetails(idExamen));
    }

}
