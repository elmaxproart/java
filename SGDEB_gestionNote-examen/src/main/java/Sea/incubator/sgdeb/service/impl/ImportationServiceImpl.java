package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.external.FiliereDTO;
import Sea.incubator.sgdeb.external.UEDTO;
import Sea.incubator.sgdeb.model.GrilleExamen;
import Sea.incubator.sgdeb.model.Importation;
import Sea.incubator.sgdeb.model.Note;
import Sea.incubator.sgdeb.repository.GrilleExamenRepository;
import Sea.incubator.sgdeb.repository.ImportationRepository;
import Sea.incubator.sgdeb.repository.NoteRepository;
import Sea.incubator.sgdeb.service.ExamenService;
import Sea.incubator.sgdeb.service.ImportationService;
import Sea.incubator.sgdeb.service.NoteService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor
public class ImportationServiceImpl implements ImportationService {

    private final RestTemplate restTemplate;
   private final ExamenService examenService;
   private  final ImportationRepository importationRepository;
   private final NoteRepository noteRepository;
   private final GrilleExamenRepository examenRepository;
   private final NoteService noteService;

    /**
     * Importe les notes d'un examen via un fichier Excel.
     * Le fichier Excel doit contenir les colonnes suivantes:
     * - Matricule (colonne 0)
     * - Nom (colonne 1)
     * - Note (colonne 2)
     * L'importation cr e un objet Importation qui contient les informations de l'importation,
     * ainsi que la liste des notes import es.
     * Les notes sont anonymis es en attribuant un identifiant unique (Anonimat) chaque note.
     *
     * @return la liste des notes import es
     */


    /**
     * Importe les notes d'un examen via un fichier Excel.
     * Le fichier Excel doit contenir les colonnes suivantes:
     * - Matricule (colonne 0)
     * - Nom (colonne 1)
     * - Note (colonne 2)
     * L'importation cr e un objet Importation qui contient les informations de l'importation,
     * ainsi que la liste des notes import es.
     * Les notes sont anonymis es en attribuant un identifiant unique (Anonimat) chaque note.
     * @param file le fichier Excel contenant les notes
     * @param idExamen l'ID de l'examen pour lequel les notes sont import es
     * @return la liste des notes import es
     */
    @Override
    public List<Note> importationFromExel(MultipartFile file, UUID idExamen) {
        // Récupérer l'examen par son id
        GrilleExamen examen = examenRepository.findById(idExamen).orElse(null);
        if (examen == null) {
            throw new RuntimeException("L'examen est introuvable");
        }

        // Créer un enregistrement d'importation
        Importation importation = new Importation();
        importation.setDateImportation(Date.valueOf(LocalDate.now(ZoneId.systemDefault())));
        importation.setNoteSur(examen.getNoteSur());
        importation.setCheminAcces(file.getOriginalFilename());
        importation.setTypeEval(examen.getTypeEval());
        importation.setCodeUE(examen.getCodeUE());
        importation.setCodeFil(examen.getCodeFiliere());
        importation.setAnneeAcademic(examen.getAnneeAcademic());

        importationRepository.save(importation);

        List<Note> importedNotes = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;  // Sauter l'en-tête

                Note note = new Note();

                // Récupérer le matricule (colonne 0)
                Cell matriculeCell = row.getCell(0);
                if (matriculeCell != null) {
                    note.setMatricule(matriculeCell.getStringCellValue());
                }

                // Récupérer le nom (colonne 1)
                Cell nomCell = row.getCell(1);
                if (nomCell != null) {
                    note.setNom(nomCell.getStringCellValue());
                }

                // Récupérer le niveau (colonne 3)
                Cell nivCell = row.getCell(3);
                if (nivCell != null) {
                    note.setNiveau(nivCell.getStringCellValue());
                }

                // Assigner les données à partir de l'examen
                note.setUE(examen.getCodeUE());
                note.setFiliere(examen.getCodeFiliere());
                note.setAnneAcademique(examen.getAnneeAcademic());
                note.setSession(examen.getSession());
                note.setSemestre(examen.getSemestre());
                note.setTypeEval(examen.getTypeEval().name());
                note.setExamenNoteSur(examen.getNoteSur());
                note.setCredit(examen.getCredit());

                // Récupérer la note (colonne 2)
                Cell noteCell = row.getCell(2);
                if (noteCell != null) {
                    if (noteCell.getCellType() == CellType.NUMERIC) {
                        note.setNotes(noteCell.getNumericCellValue());
                    } else if (noteCell.getCellType() == CellType.STRING) {
                        try {
                            note.setNotes(Double.parseDouble(noteCell.getStringCellValue()));
                        } catch (NumberFormatException e) {
                            // Gérer l'erreur de conversion
                        }
                    }
                }

                // Assigner l'importation et la grille d'examen à la note
                note.setImportation(importation);
                note.setIdGrilleExamen(examen);

                // Générer l'anonymat de manière unique
                Long newAnonimat = 1L;
                Optional<Note> existingNote = noteRepository.findByAnonimat(newAnonimat.toString());
                while (existingNote.isPresent()) {
                    newAnonimat++;
                    existingNote = noteRepository.findByAnonimat(newAnonimat.toString());
                }
                note.setAnonimat(newAnonimat.toString());

                // Vérifier si une note existe déjà pour ce matricule et cet anonymat
                Optional<Note> existingNote2 = noteRepository.findByMatriculeAndAnonimat(note.getMatricule(), note.getAnonimat());
                if (existingNote2.isPresent()) {
                    continue;  // Passer cette note si elle existe déjà
                }


                // Sauvegarder la note
                noteRepository.save(note);
                importedNotes.add(note);
                GrilleExamen examen1 = examenRepository.findById(idExamen).orElse(null);
                assert examen1 != null;
                examen1.setStatut(1);
                examen1.setDateImportation(importation.getDateImportation());
                examenRepository.save(examen1);
            }
            importation.setGrille(examen);
            importationRepository.save(importation);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return importedNotes;
    }

    @Override
    public UEDTO getUEByCode(String codeUE) {
        String ueServiceUrl="http://localhost:1111/api/academics/ues/code/{code}";
        ueServiceUrl=ueServiceUrl.replace("{code}",codeUE);
        return restTemplate.getForObject(ueServiceUrl, UEDTO.class);
    }

    @Override
    public Importation importationDetails(UUID idExamen) {

      GrilleExamen examen=examenRepository.findById(idExamen).orElse(null);
        assert examen != null;
        return importationRepository.findByCodeUEAndCodeFilAndDateImportationAndGrilleId(examen.getCodeUE(),examen.getCodeFiliere(),examen.getDateImportation(),examen.getId());

    }


    // Méthode pour trouver la première ligne non nulle
    private int findFirstNonEmptyRow(Sheet sheet) {
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { // Commence à 1 pour ignorer l'entête
            Row row = sheet.getRow(i);
            if (row != null) {
                return i; // Retourne l'indice de la première ligne non nulle
            }
        }
        return -1; // Aucune ligne non nulle trouvée fichier vide
    }

    /**
     * Recherche une UE par son code et sa filière.
     * @param nomUe le code de l'UE
     * @param filiere la filière de l'UE
     * @return l'objet UEDTO correspondant, ou null si non trouvé
     */
    @Override
    public UEDTO findByCodeUeAndFiliere(String nomUe, FiliereDTO filiere) {
        String ueServiceUrl="http://localhost:1111/api/academics/ues/";
        String url = ueServiceUrl + nomUe +"/"+ filiere;
        return restTemplate.getForObject(url, UEDTO.class);
    }

    /**
     * Recherche une filière par son code.
     * @param code le code de la filière
     * @return l'objet FiliereDTO correspondant, ou null si non trouvé
     */
    @Override
    public FiliereDTO findByCode (String code) {
        String filiereUrl="http://localhost:1111/api/academics/filieres/"+code+"/codefil";
        return restTemplate.getForObject(filiereUrl, FiliereDTO.class);
    }



}

