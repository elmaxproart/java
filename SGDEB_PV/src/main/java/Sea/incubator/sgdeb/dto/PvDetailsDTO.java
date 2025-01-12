package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.model.PvUE;
import lombok.Data;

@Data
public class PvDetailsDTO {
    private int id;
    private String matricule;
    private String nom;
    private double note;
    private String niveau;
    PvUE pvUE;
}
