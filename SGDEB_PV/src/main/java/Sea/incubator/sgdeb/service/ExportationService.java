package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.dto.PvDetailsDTO;
import Sea.incubator.sgdeb.dto.PvDetailsRecapDTO;
import Sea.incubator.sgdeb.model.PvRecap;
import Sea.incubator.sgdeb.model.PvUE;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExportationService {
    /**
     * @return NoteDTO List
     */


    void ExportationEnPdf(String directoryPath,String codeUE,String typeEval);

    List<PvUE> LoardDataToExportPv();

    void exportRecapToPdf(String filePath, String libelle);

    List<PvRecap>loadDataFromPvRecap();

    List<PvUE> getAll();

    List<PvRecap> getAllRecap();

    PvRecap putGrade();

    List<PvDetailsDTO> getPvDetails();

    List<PvDetailsRecapDTO> getPvDetailsRecap();
    PvRecap updateStudentGrades(String matricule);
}
