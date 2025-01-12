package Sea.incubator.sgdeb.dto;


import Sea.incubator.sgdeb.external.GrilleExamenDTO;
import Sea.incubator.sgdeb.model.enumType.Nature;
import Sea.incubator.sgdeb.model.enumType.Semestre;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CorrectionDto {

    private UUID id;
    private Double note;
    private String matricule;
    private String nom;
    private  Integer anonymat;
    private boolean status;
    private Semestre semestre;
    private String ue;
    private Nature nature;

}