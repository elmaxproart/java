package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.model.PvRecap;
import lombok.Data;

@Data
public class PvDetailsRecapDTO {

    private int id;
    private String matricule;
    private String nom;
    private double mgp;
    private String niveau;

    private PvRecap pvRecap;
}
