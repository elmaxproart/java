package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.external.FiliereDTO;
import Sea.incubator.sgdeb.external.GrilleDTO;
import Sea.incubator.sgdeb.external.UEDTO;
import Sea.incubator.sgdeb.external.enumType.TypeEval;
import lombok.Data;

import java.util.UUID;

@Data
public class GrilleExamenDTO {

    private UUID id;



    private TypeEval typeEval;
    private int noteSur;
    private int statut;
    private String session;
    private int semestre;
    private int credit;



}