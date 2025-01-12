package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.dto.PvDetailsDTO;
import Sea.incubator.sgdeb.dto.PvDetailsRecapDTO;
import Sea.incubator.sgdeb.external.*;
import Sea.incubator.sgdeb.model.*;
import Sea.incubator.sgdeb.model.enumType.TypeEval;
import Sea.incubator.sgdeb.repository.ObservationRepository;
import Sea.incubator.sgdeb.repository.PvDetailRepository;
import Sea.incubator.sgdeb.repository.PvRecapRepository;
import Sea.incubator.sgdeb.repository.PvRepository;
import Sea.incubator.sgdeb.service.ExportationService;
import Sea.incubator.sgdeb.tools.RestTamplateAcces;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service

public class ExportationServiceImpl implements ExportationService {

    private final RestTamplateAcces restTamplateAcces;
    private final PvRepository pvRepository;
    private final PvRecapRepository pvRecapRepository;
    private final PvDetailRepository pvDetailRepository;
    private final PvDetailsRecapRepository pvDetailsRecapRepository;
    private final ObservationRepository observationRepository;
    private List<PvRecap> loadDataFromPvREcapRepository() {
        return pvRecapRepository.findAll();
    }

    @Override
    public List<PvUE> getAll() {
        return pvRepository.findAll();
    }
    @Transactional
    @Override
    public List<PvRecap> getAllRecap() {
        List<PvRecap> pvRecapList = pvRecapRepository.findAll();

        // Initialiser les collections paresseuses
        pvRecapList.forEach(pv -> {
            Hibernate.initialize(pv.getNotes()); // Initialisation explicite de la collection 'notes'
            Hibernate.initialize(pv.getUeGradesSur100()); // Initialisation de 'ueGradesSur100' si elle est lazy
        });

        return pvRecapList;
    }

    @Override
    public PvRecap putGrade() {
        return null;
    }

    @Override
    public List<PvDetailsDTO> getPvDetails() {
        // Récupérer toutes les entités PvDetails
        List<PvDetails> pvDetailsList = pvDetailRepository.findAll();

        // Convertir les entités en DTOs
        List<PvDetailsDTO> pvDetailsDTOList = pvDetailsList.stream().map(pvDetails -> {
            PvDetailsDTO dto = new PvDetailsDTO();
            dto.setId(pvDetails.getId());
            dto.setMatricule(pvDetails.getMatricule());
            dto.setNom(pvDetails.getNom());
            dto.setNote(pvDetails.getNote());
            dto.setNiveau(pvDetails.getNiveau());
            dto.setPvUE(pvDetails.getPvUE()); // Si PvUE est une entité que vous devez inclure
            return dto;
        }).collect(Collectors.toList());

        return pvDetailsDTOList;
    }

    @Override
    public List<PvDetailsRecapDTO> getPvDetailsRecap() {
        // Récupérer toutes les entités PvDetailsRecap
        List<PvDetailsRecap> pvDetailsRecapList = pvDetailsRecapRepository.findAll();

        // Convertir les entités en DTOs
        List<PvDetailsRecapDTO> pvDetailsRecapDTOList = pvDetailsRecapList.stream().map(pvDetailsRecap -> {
            PvDetailsRecapDTO dto = new PvDetailsRecapDTO();
            dto.setId(pvDetailsRecap.getId());
            dto.setMatricule(pvDetailsRecap.getMatricule());
            dto.setNom(pvDetailsRecap.getNom());
            dto.setMgp(pvDetailsRecap.getMgp());
            dto.setNiveau(pvDetailsRecap.getNiveau());
            dto.setPvRecap(pvDetailsRecap.getPvRecap()); // Inclure PvRecap si nécessaire
            return dto;
        }).collect(Collectors.toList());

        return pvDetailsRecapDTOList;
    }


    //exportation de pv ............................................................................................


    @Override
    public void ExportationEnPdf(String directoryPath, String codeUE, String typeEval) {
        // Récupérer les PVs depuis le repository
        List<PvUE> allPvs = loadDataFromPvRepository();

        // Filtrer les PVs par codeUE et typeEval tout en gérant les valeurs nulles
        List<PvUE> filteredPvs = allPvs.stream()
                .filter(pv -> Objects.nonNull(pv.getCodeUE()) && pv.getCodeUE().equalsIgnoreCase(codeUE))
                .filter(pv -> Objects.nonNull(pv.getTypeEval()) && pv.getTypeEval().equalsIgnoreCase(typeEval))
                .toList();
        if (filteredPvs.isEmpty()) {
            System.out.println("Aucun PV correspondant aux critères de recherche n'a été trouvé.");
            return;
        }

        List<NoteDTO> importedGrades = restTamplateAcces.getAllGradeWithAnonymat();

        if (!filteredPvs.isEmpty()) {
            Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(directoryPath));
                document.open();

                // En-tête de l'université avec logo
                PdfPTable headerTable = new PdfPTable(3);
                headerTable.setWidthPercentage(100);
                headerTable.setWidths(new float[]{2, 6, 2});
                Image logo = Image.getInstance("C:\\Users\\SMARTech\\Desktop\\test.jpeg");
                logo.scaleToFit(50, 50);
                PdfPCell logoCell = new PdfPCell(logo);
                logoCell.setBorder(Rectangle.NO_BORDER);
                logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                headerTable.addCell(logoCell);

                Font universityFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.DARK_GRAY);
                PdfPCell universityInfoCell = new PdfPCell(new Paragraph("Université Fictive\nFaculté des Sciences\nDépartement d'Informatique", universityFont));
                universityInfoCell.setBorder(Rectangle.NO_BORDER);
                universityInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerTable.addCell(universityInfoCell);

                PdfPCell emptyCell = new PdfPCell();
                emptyCell.setBorder(Rectangle.NO_BORDER);
                headerTable.addCell(emptyCell);
                document.add(headerTable);
                document.add(new Paragraph("\n")); // Espacement après l'en-tête

                Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.DARK_GRAY);
                Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);
                Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.WHITE);
                Font cellFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);

                // Titre et informations académiques
                document.add(new Paragraph("PROCES VERBAL DE L'UNITE D'ENSEIGNEMENT\n" + codeUE, titleFont));
                document.add(new Paragraph("\nAnnee Academique: " + importedGrades.get(0).getAnneAcademique() + "  |  GRADE: " + importedGrades.get(0).getNiveau(), sectionFont));
                document.add(new Paragraph("Filiere: " + importedGrades.get(0).getFiliere() + "  |  SEMESTRE: " + importedGrades.get(0).getSemestre(), sectionFont));
                document.add(new Paragraph("\n\n", sectionFont));

                // Création du tableau
                PdfPTable table = new PdfPTable(12);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{1, 2, 5, 2, 2, 2, 2, 2, 2, 2, 2, 2});
                table.setHorizontalAlignment(Element.ALIGN_CENTER);

                // En-têtes de colonnes
                String[] headers = {"Num", "Matricule", "Nom et Prénom", "Niveau", "ANO CC", "Note CC", "ANO EE", "Note EE", "ANO EP", "Note EP", "DEC", "Mention"};
                for (String colTitle : headers) {
                    PdfPCell header = new PdfPCell(new Phrase(colTitle, headerFont));
                    header.setBackgroundColor(BaseColor.GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                }

                // Remplissage des données du tableau, après le filtrage
                int num = 1;
                for (PvUE pv : filteredPvs) {
                    table.addCell(new Phrase(String.valueOf(num), cellFont));
                    table.addCell(new Phrase(pv.getMatricule(), cellFont));
                    table.addCell(new Phrase(pv.getNomPrenom(), cellFont));
                    table.addCell(new Phrase(pv.getNiveau(), cellFont));
                    table.addCell(new Phrase(pv.getANO_CC() != null ? pv.getANO_CC() : "", cellFont));
                    table.addCell(new Phrase(String.valueOf(pv.getNoteCCsur()), cellFont));
                    table.addCell(new Phrase(pv.getANO_EE() != null ? pv.getANO_EE() : "", cellFont));
                    table.addCell(new Phrase(String.valueOf(pv.getNoteEEsur()), cellFont));
                    table.addCell(new Phrase(pv.getANO_EP() != null ? pv.getANO_EP() : "", cellFont));
                    table.addCell(new Phrase(String.valueOf(pv.getNoteEPsur()), cellFont));
                    table.addCell(new Phrase(pv.getDEC(), cellFont));
                    table.addCell(new Phrase(pv.getMention(), cellFont));
                    num++;
                }

                document.add(table);
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private List<PvUE> loadDataFromPvRepository() {
        return pvRepository.findAll();
    }

    public List<PvUE> LoardDataToExportPv() {
        List<PvUE> exportedPvs = new ArrayList<>();
        List<NoteDTO> importedGrades = restTamplateAcces.getAllGradeWithAnonymat();
        PvDetails pvDetails=new PvDetails();
        for (NoteDTO noteDTO : importedGrades) {
            PvUE pv = new PvUE();
            pv.setMatricule(noteDTO.getMatricule());
            pv.setNomPrenom(noteDTO.getNom());
            pv.setNiveau(noteDTO.getNiveau());

            switch (noteDTO.getTypeEval()) {
                case CC:
                    pv.setANO_CC(noteDTO.getAnonimat());
                    pv.setNoteCCsur(noteDTO.getNotes());
                    pvDetails.setNote(pv.getNoteCCsur());
                    pv.setTypeEval("CC");
                    pv.setCodeUE(noteDTO.getUE());

                    break;

                case EE:
                    pv.setANO_EE(noteDTO.getAnonimat());
                    pv.setNoteEEsur(noteDTO.getNotes());
                    pv.setDEC(determineDEC(noteDTO.getNotes()));
                    pv.setMention(determineMention(noteDTO.getNotes()));
                    pvDetails.setNote(pv.getNoteEEsur());
                    pv.setTypeEval("EE");
                    pv.setCodeUE(noteDTO.getUE());
                    break;

                case EP:
                    pv.setANO_EP(noteDTO.getAnonimat());
                    pv.setNoteEPsur(noteDTO.getNotes());
                    pv.setDEC(determineDEC(noteDTO.getNotes()));
                    pvDetails.setNote(pv.getNoteEPsur());
                    pv.setMention(determineMention(noteDTO.getNotes()));
                    pv.setTypeEval("EP");
                    pv.setCodeUE(noteDTO.getUE());
                    break;
            }
            //details of pv
            pvDetails.setNom(pv.getNomPrenom());
            pvDetails.setNiveau(pv.getNiveau());
            pvDetails.setMatricule(pvDetails.getMatricule());
            pvDetails.setPvUE(pv);


            exportedPvs.add(pvRepository.save(pv));
            pvDetailRepository.save(pvDetails);
        }

        return exportedPvs;
    }



    private String determineDEC(double note) {
        if (note < 35) return "NC";
        if (note < 50) return "CANT";
        return "CA";
    }

    private String determineMention(double note) {
        if (note < 30) return "F";
        if (note < 35) return "E";
        if (note < 40) return "D";
        if (note < 45) return "C-";
        if (note < 50) return "C";
        if (note < 55) return "C+";
        if (note < 60) return "B-";
        if (note < 65) return "B";
        if (note < 70) return "B+";
        if (note < 75) return "A-";
        return "A";
    }
//pv REcap ......................................................................................................
@Override
public void exportRecapToPdf(String filePath, String libelle) {
    // Charger les PV Recap depuis la base de données
    List<PvRecap> exportedPvs = pvRecapRepository.findAll();
    GrilleDTO grilleDTO = restTamplateAcces.getGrille(libelle);
    // Appliquer les filtres sur la base des paramètres reçus
    List<PvRecap> filteredPvs = exportedPvs.stream()
            .filter(pv -> pv.getNiveaux().equalsIgnoreCase(grilleDTO.getNiveau().toString()))         // Filtre par niveau
            .filter(pv -> pv.getCodeFil().equalsIgnoreCase(grilleDTO.getFiliere().getCode()))        // Filtre par filière
            .filter(pv -> pv.getAnnee().equalsIgnoreCase(grilleDTO.getAnneeAcademic().getLibelle()))  // Filtre par année académique
            .collect(Collectors.toList());

    // Charger les UE et les notes correspondantes

    List<UEDTO> ueList = restTamplateAcces.getAllUEWithGrille(grilleDTO.getId());
    List<NoteDTO> importedGrades = restTamplateAcces.getAllGradeWithAnonymat();

    if (importedGrades.isEmpty()) {
        System.out.println("L'exportation du Pv n'est pas possible.");
        System.err.println("Aucune note à exporter.");
        return;
    }

    if (filteredPvs.isEmpty()) {
        System.out.println("L'exportation du Pv n'est pas possible.");
        System.err.println("Pas de données à exporter pour les critères donnés.");
        return;
    }

    if (ueList.isEmpty()) {
        System.out.println("L'exportation du Pv n'est pas possible.");
        System.err.println("Aucune UE à exporter.");
        return;
    }

    // Créer le document PDF
    Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
    try {
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        createHeader(document);  // Crée l'entête du document

        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.DARK_GRAY);
        Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);

        PdfPTable table = createTable(ueList, filteredPvs, importedGrades);
        document.add(table);
    } catch (DocumentException | IOException e) {
        e.printStackTrace();
    } finally {
        document.close(); // Fermeture du document
    }
}


    private void createHeader(Document document) throws DocumentException, IOException {
        PdfPTable headerTable = new PdfPTable(3);  // Crée un tableau avec 3 colonnes
        headerTable.setWidthPercentage(100);  // Définit la largeur à 100% de la page
        headerTable.setWidths(new float[]{2, 6, 2});  // Définit les proportions des colonnes
        List<NoteDTO> importedGrades = restTamplateAcces.getAllGradeWithAnonymat();
        // Chargement du logo (assurez-vous que le chemin est correct)
        String imagePath = "src" + File.separator + "main" + File.separator + "java" + File.separator + "Sea" + File.separator + "incubator" + File.separator + "sgdeb" + File.separator + "asset" + File.separator + "test.jpeg";
        Image logo = Image.getInstance(imagePath);
        logo.scaleToFit(60, 60);  // Redimensionne le logo
        PdfPCell logoCell = new PdfPCell(logo);
        logoCell.setBorder(Rectangle.NO_BORDER);  // Supprime les bordures du logo
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);  // Centre le logo
        headerTable.addCell(logoCell);

        // Cellule contenant les informations de l'université
        Font universityFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

        // Ajoute les informations dans la cellule centrale
        final String ueLibelle = "COMPUTER ARCHITECTURE";
        final String republic = "CAMEROON";
        String universityInfo = "REPUBLIC OF " + republic + "\nPeace - Work - Fatherland\n\n"
                + "UNIVERSITY OF YAOUNDE I\nFACULTY OF SCIENCES\n\n"
                + "PROCES VERBAL DE CORRECTION DE L'UNITE D'ENSEIGNEMENT\n"
                +importedGrades.get(0).getUE()+ " - " + ueLibelle + "\n"

                + "GRADE: "+importedGrades.get(0).getNiveau()+" SEMESTRE: "+importedGrades.get(0).getSemestre()+"  Année académique: "+importedGrades.get(0).getAnneAcademique();

        PdfPCell universityInfoCell = new PdfPCell(new Paragraph(universityInfo, universityFont));
        universityInfoCell.setBorder(Rectangle.NO_BORDER);  // Supprime les bordures
        universityInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);  // Centre le texte
        headerTable.addCell(universityInfoCell);

        // Cellule vide à droite (ou vous pouvez ajouter un autre logo ou texte si nécessaire)
        PdfPCell emptyCell = new PdfPCell();
        emptyCell.setBorder(Rectangle.NO_BORDER);  // Supprime les bordures
        headerTable.addCell(emptyCell);

        // Ajoute le tableau de l'en-tête au document
        document.add(headerTable);

        // Ajoute un saut de ligne pour l'espacement après l'en-tête
        document.add(new Paragraph("\n"));

        // Ajouter les informations supplémentaires (optionnel, dépendant de ce que vous voyez sur l'image)
        final String libelle = " Information and Communication Technology for Development";
        Paragraph additionalInfo = new Paragraph("FILIERE: "+importedGrades.get(0).getFiliere()+ " -" + libelle, infoFont);
        additionalInfo.setAlignment(Element.ALIGN_CENTER);  // Centrer le texte
        document.add(additionalInfo);

        // Un autre saut de ligne pour espacer
        document.add(new Paragraph("\n"));
    }


    private PdfPTable createTable(List<UEDTO> ueList, List<PvRecap> exportedPvs, List<NoteDTO> importedGrades) throws DocumentException {
        int totalColumns = 4 + ueList.size() + 6; // Base columns + dynamic UEs + extra columns
        PdfPTable table = new PdfPTable(totalColumns);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Column widths configuration
        float[] columnWidths = new float[totalColumns];
        columnWidths[0] = 1f;  // Num
        columnWidths[1] = 3f;  // Matricule
        columnWidths[2] = 3f;  // Nom
        columnWidths[3] = 2f;  // Niveau

        // Width for dynamic UEs
        for (int i = 4; i < 4 + ueList.size(); i++) {
            columnWidths[i] = 2f;  // Dynamic UEs
        }

        // Extra column widths
        for (int i = 4 + ueList.size(); i < totalColumns; i++) {
            columnWidths[i] = 2f;  // Cap%, MGP, etc.
        }

        table.setWidths(columnWidths);

        // Fonts for headers and cells
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.WHITE);
        Font cellFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);

        // Base column headers
        String[] headers = {"Num", "Matricule", "Nom", "Niveau"};
        for (String colTitle : headers) {
            PdfPCell header = new PdfPCell(new Phrase(colTitle, headerFont));
            header.setBackgroundColor(BaseColor.GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        }

        // Dynamic UE headers
        for (UEDTO ue : ueList) {
            PdfPCell header = new PdfPCell(new Phrase(ue.getCode(), headerFont));  // Use UE code
            header.setBackgroundColor(BaseColor.GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        }

        // Extra column headers
        String[] headers2 = {"Cap%", "MGP/4", "Moy/100", "MENTIONS", "MOY/20", "DEC"};
        for (String colTitle : headers2) {
            PdfPCell header = new PdfPCell(new Phrase(colTitle, headerFont));
            header.setBackgroundColor(BaseColor.GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        }

        // Fill the rows with student data and grades
        int num = 1;
        for (PvRecap pv : exportedPvs) {
            Hibernate.initialize(pv.getUeGradesSur100());

            // Student base data
            table.addCell(new Phrase(String.valueOf(num), cellFont));  // Num
            table.addCell(new Phrase(pv.getMatricule(), cellFont));    // Matricule
            table.addCell(new Phrase(pv.getNom(), cellFont));          // Nom
            table.addCell(new Phrase(pv.getNiveaux(), cellFont));      // Niveau

            // Fill dynamic UE grade columns
            for (UEDTO ue : ueList) {
                Map<String, Double> ueGradesSur100 = pv.getUeGradesSur100();  // UE grades map
                String ueKey = ue.getCode() + "Sur100";  // Map key
                Double noteSur100 = ueGradesSur100 != null ? ueGradesSur100.get(ueKey) : null;  // Fetch grade

                // Display the grade or "N/A" if not available
                String noteDisplay = (noteSur100 != null) ? String.format("%.2f", noteSur100) : "N/A";
                PdfPCell cell = new PdfPCell(new Phrase(noteDisplay, cellFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
            }

            // Additional columns
            table.addCell(new Phrase(String.format("%.2f", pv.getPourcentageCap()), cellFont)); // Cap%
            table.addCell(new Phrase(String.format("%.2f", pv.getMgp()), cellFont)); // MGP/4

            // Display either "Moy/100" and "Mention" based on observation code
            if (pv.getObservation().getCode() == 0) {
                table.addCell(new Phrase(String.format("%.2f", pv.getNoteSur100()), cellFont)); // Moy/100
                table.addCell(new Phrase(pv.getMention(), cellFont)); // Mention
            } else {
                table.addCell(new Phrase("0.0", cellFont)); // Moy/100
                String mention = "N/A";
                if (pv.getObservation().getCode() == 3) mention = "EL";
                else if (pv.getObservation().getCode() == 2) mention = "EP";
                else if (pv.getObservation().getCode() == 1) mention = "ENA";
                table.addCell(new Phrase(mention, cellFont)); // Mention
            }

            table.addCell(new Phrase(String.format("%.2f", pv.getMoyenne()), cellFont)); // MOY/20
            table.addCell(new Phrase(pv.getDecision(), cellFont)); // DEC

            num++;
        }

        return table;
    }

    public NoteDTO getNoteForUEAndStudent(String matricule, String ueCode, List<NoteDTO> notes) {
        return notes.stream()
                .filter(note -> note.getMatricule().equals(matricule) && note.getUE().equals(ueCode))
                .findFirst()
                .orElse(null);
    }
    /**
     * Charge les données pour l'exportation du PV.
     *
     * @return La liste des PV à exporter.
     */@Override
    @Transactional
    public List<PvRecap> loadDataFromPvRecap() {
        List<PvRecap> exportedPvs = new ArrayList<>();
        List<NoteDTO> importedGrades = restTamplateAcces.getAllGradeWithAnonymat();


        if (importedGrades == null || importedGrades.isEmpty()) {
            System.out.println("No grades imported.");
            return exportedPvs;
        }

        Map<String, List<NoteDTO>> gradesByMatricule = importedGrades.stream()
                .collect(Collectors.groupingBy(NoteDTO::getMatricule));

        for (String matricule : gradesByMatricule.keySet()) {
            try {
                List<NoteDTO> studentGrades = gradesByMatricule.get(matricule);

                if (studentGrades == null || studentGrades.isEmpty()) {
                    System.out.println("No grades found for student with matricule: " + matricule);
                    continue;
                }

                Optional<PvRecap> existingPvRecap = pvRecapRepository.findByMatricule(matricule);
                Optional<PvDetailsRecap> existingPvDetailsRecap = pvDetailsRecapRepository.findByMatricule(matricule);

                if (existingPvRecap.isPresent() || existingPvDetailsRecap.isPresent()) {
                    System.out.println("Skipping existing student with matricule: " + matricule);
                    continue;
                }

                PvRecap pvRecap = new PvRecap();
                pvRecap.setMatricule(matricule);
                pvRecap.setNom(studentGrades.get(0).getNom());
                pvRecap.setNiveaux(studentGrades.get(0).getNiveau());
                pvRecap.setAnnee(importedGrades.get(0).getAnneAcademique());
                pvRecap.setCodeFil(importedGrades.get(0).getFiliere());

                double totalNoteSur100 = 0;
                int ueCount = 0;
                double totalMoyenneSur20 = 0;
                Map<String, Double> ueGradesSur100 = new HashMap<>();

                Map<String, List<NoteDTO>> gradesByUE = studentGrades.stream()
                        .collect(Collectors.groupingBy(NoteDTO::getUE));

                for (String ue : gradesByUE.keySet()) {
                    List<NoteDTO> ueGrades = gradesByUE.get(ue);

                    if (ueGrades == null || ueGrades.isEmpty()) {
                        System.out.println("No grades found for UE: " + ue);
                        continue;
                    }

                    // Calculating final grade for the UE
                    double noteSur100 = calculateFinalGradeForStudent(ueGrades, ueGrades.get(0).getExamenNoteSur());
                    ueGradesSur100.put(ue + "Sur100", noteSur100);

                    totalNoteSur100 += noteSur100;
                    totalMoyenneSur20 += noteSur100 / 5;  // Convert note/100 to note/20
                    ueCount++;
                }

                double noteFinaleSur100 = ueCount != 0 ? totalNoteSur100 / ueCount : 0.0;
                double moyenneSur20 = ueCount != 0 ? totalMoyenneSur20 / ueCount : 0.0;

                // Setting note/100 and moyenne/20
                pvRecap.setNoteSur100(noteFinaleSur100);
                pvRecap.setMoyenne(moyenneSur20);

                // Attaching the UE grades to PvRecap
                pvRecap.setUeGradesSur100(ueGradesSur100);

                // Calculate MGP based on moyenne/20
                double mgp = calculateGradeOnFour(noteFinaleSur100);
                pvRecap.setMgp(mgp);

                // Set mention based on MGP
                String mention = getMention(mgp);
                pvRecap.setMention(mention);

                // Set decision based on MGP
                String decision = (mgp >= 2.0) ? "Admis" : "Echec";
                pvRecap.setDecision(decision);

                // Set observation
                Observation observation = new Observation();
                EtudiantDTO etudiant = restTamplateAcces.studentExitByMatricule(matricule);
                if (etudiant == null) {
                    observation.setDescription("Étudiant non trouvé dans le système.");
                    observation.setCode(1);
                } else {
                    observation.setDescription("Étudiant existe dans le système.");
                    observation.setCode(0);
                }
                observation = observationRepository.save(observation);
                pvRecap.setObservation(observation);

                // Add the PvRecap to the exported list and save to the database
                exportedPvs.add(pvRecap);
                pvRecapRepository.save(pvRecap);
                System.out.println("Saved PvRecap for matricule: " + matricule);

                PvDetailsRecap pvDetailsRecap = new PvDetailsRecap();
                pvDetailsRecap.setPvRecap(pvRecap);
                pvDetailsRecap.setMatricule(matricule);
                pvDetailsRecap.setNom(pvRecap.getNom());
                pvDetailsRecap.setMgp(pvRecap.getMgp());
                pvDetailsRecap.setNiveau(pvRecap.getNiveaux());
                pvDetailsRecapRepository.save(pvDetailsRecap);
                System.out.println("Saved PvDetailsRecap for matricule: " + matricule);

            } catch (Exception e) {
                System.out.println("Error processing matricule: " + matricule + ". Error: " + e.getMessage());
            }
        }

        return exportedPvs;
    }


    // Vérification si une UE a déjà une note >= 50 pour cet étudiant


    // Méthode pour calculer la note finale sur 100 à partir des différentes notes de l'UE (CC, EP, EE)
    private double calculateFinalGradeForStudent(List<NoteDTO> ueGrades, int examenNoteSur) {
        double cc = 0, ep = 0, ee = 0;
        double noteSur100 = 0;

        // Récupération de toutes les notes importées
        List<NoteDTO> importedGrades = restTamplateAcces.getAllGradeWithAnonymat();

        // Extraction des informations d'un des objets ueGrades (assumption: les infos sont identiques pour toutes les notes d'un même étudiant pour un UE)
        if (ueGrades.isEmpty()) {
            return noteSur100; // Si la liste est vide, retourner 0
        }

        NoteDTO exampleNote = ueGrades.get(0); // On prend un exemple pour récupérer les infos d'un étudiant
        String matricule = exampleNote.getMatricule();
        String filiere = exampleNote.getFiliere();
        String ue = exampleNote.getUE();
        String niveau = exampleNote.getNiveau();
        String anneeAcademique = exampleNote.getAnneAcademique();
        String session = exampleNote.getSession();
        int semestre = exampleNote.getSemestre();

        // Filtrer les notes pour obtenir uniquement celles correspondant à l'étudiant et aux critères spécifiques
        List<NoteDTO> filteredGrades = importedGrades.stream()
                .filter(note -> note.getMatricule().equals(matricule)
                        && note.getFiliere().equals(filiere)
                        && note.getUE().equals(ue)
                        && note.getNiveau().equals(niveau)
                        && note.getAnneAcademique().equals(anneeAcademique)
                        && note.getSession().equals(session)
                        && note.getSemestre() == semestre)
                .toList();

        // Filtrer par type d'évaluation
        List<NoteDTO> noteCC = filteredGrades.stream()
                .filter(note -> note.getTypeEval().equals(TypeEval.CC))
                .toList();

        List<NoteDTO> noteEE = filteredGrades.stream()
                .filter(note -> note.getTypeEval().equals(TypeEval.EE))
                .toList();

        List<NoteDTO> noteEP = filteredGrades.stream()
                .filter(note -> note.getTypeEval().equals(TypeEval.EP))
                .toList();

        // Si l'étudiant a des notes pour CC, EP, EE
        if (!(noteCC.isEmpty() && noteEE.isEmpty() && noteEP.isEmpty())) {
            // Si une note CC est présente, la normaliser et la pondérer
            if (!noteCC.isEmpty()) {
                cc = (noteCC.get(0).getNotes() / examenNoteSur) * 100;  // Normalisation sur 100
                noteSur100 += cc * 0.30;  // Pondération à 30%
            }

            // Si une note EP est présente, la normaliser et la pondérer
            if (!noteEP.isEmpty()) {
                ep = (noteEP.get(0).getNotes() / examenNoteSur) * 100;  // Normalisation sur 100
                noteSur100 += ep * 0.20;  // Pondération à 20%
            }

            // Si une note EE est présente, la normaliser et la pondérer
            if (!noteEE.isEmpty()) {
                ee = (noteEE.get(0).getNotes() / examenNoteSur) * 100;  // Normalisation sur 100
                noteSur100 += ee * 0.50;  // Pondération à 50%
            }
        }

        return noteSur100;
    }



    // Méthode utilitaire pour convertir une note sur 100 en une note sur 4
    private double calculateGradeOnFour(double note) {
        if (note >= 80 && note <= 100) return 4.0;
        else if (note >= 75 && note < 80) return 3.70;
        else if (note >= 70 && note < 75) return 3.30;
        else if (note >= 65 && note < 70) return 3.0;
        else if (note >= 60 && note < 65) return 2.70;
        else if (note >= 55 && note < 60) return 2.30;
        else if (note >= 50 && note < 55) return 2.0;
        else if (note >= 45 && note < 50) return 1.70;
        else if (note >= 40 && note < 45) return 1.30;
        else if (note >= 35 && note < 40) return 1.0;
        else return 0.0;
    }

    // Utility method to determine mention based on MGP
    private String getMention(double mgp) {
        if (mgp >= 3.7) return "Très Bien";
        if (mgp >= 3.3) return "Bien";
        if (mgp >= 3.0) return "Assez Bien";
        if (mgp >= 2.4) return "Assez Bien";
        if (mgp >= 2.3) return "Passable";
        if (mgp >= 2.0) return "Passable";
        return "Echec";
    }
    @Transactional
    public PvRecap updateStudentGrades(String matricule) {
        Optional<PvRecap> existingPvRecapOpt = pvRecapRepository.findByMatricule(matricule);
        List<NoteDTO> updatedGrades =restTamplateAcces.getAllGradeWithAnonymat();
        if (existingPvRecapOpt.isEmpty()) {
            throw new RuntimeException("Student with matricule " + matricule + " not found.");
        }

        PvRecap pvRecap = existingPvRecapOpt.get();

        // Mettre à jour les informations des notes
        double totalNoteSur100 = 0;
        int ueCount = 0;
        double totalMoyenneSur20 = 0;
        Map<String, Double> ueGradesSur100 = new HashMap<>();

        Map<String, List<NoteDTO>> gradesByUE = updatedGrades.stream()
                .collect(Collectors.groupingBy(NoteDTO::getUE));

        for (String ue : gradesByUE.keySet()) {
            List<NoteDTO> ueGrades = gradesByUE.get(ue);

            if (ueGrades == null || ueGrades.isEmpty()) {
                System.out.println("No grades found for UE: " + ue);
                continue;
            }

            // Calcul de la note finale pour l'UE
            double noteSur100 = calculateFinalGradeForStudent(ueGrades, ueGrades.get(0).getExamenNoteSur());
            ueGradesSur100.put(ue + "Sur100", noteSur100);

            totalNoteSur100 += noteSur100;
            totalMoyenneSur20 += noteSur100 / 5;  // Convertir note/100 en note/20
            ueCount++;
        }

        double noteFinaleSur100 = ueCount != 0 ? totalNoteSur100 / ueCount : 0.0;
        double moyenneSur20 = ueCount != 0 ? totalMoyenneSur20 / ueCount : 0.0;

        // Mise à jour des champs liés aux notes
        pvRecap.setNoteSur100(noteFinaleSur100);
        pvRecap.setMoyenne(moyenneSur20);
        pvRecap.setUeGradesSur100(ueGradesSur100); // Mettre à jour la Map avec les notes par UE

        // Recalcul du MGP basé sur la nouvelle moyenne
        double mgp = calculateGradeOnFour(noteFinaleSur100);
        pvRecap.setMgp(mgp);

        // Recalcul de la mention basée sur le MGP
        String mention = getMention(mgp);
        pvRecap.setMention(mention);

        // Mise à jour de la décision en fonction du MGP
        String decision = (mgp >= 2.0) ? "Admis" : "Echec";
        pvRecap.setDecision(decision);

        // Sauvegarde de l'entité PvRecap avec les nouvelles valeurs
        pvRecapRepository.save(pvRecap);
        System.out.println("Updated PvRecap for matricule: " + matricule);

        // Mise à jour des détails associés si nécessaire
        Optional<PvDetailsRecap> existingPvDetailsRecapOpt = pvDetailsRecapRepository.findByMatricule(matricule);
        if (existingPvDetailsRecapOpt.isPresent()) {
            PvDetailsRecap pvDetailsRecap = existingPvDetailsRecapOpt.get();
            pvDetailsRecap.setMgp(mgp);
            pvDetailsRecapRepository.save(pvDetailsRecap);
            System.out.println("Updated PvDetailsRecap for matricule: " + matricule);
        }

        return pvRecap;
    }


}
