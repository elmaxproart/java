package Sea.incubator.sgdeb.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class NoteDTO {

    private UUID id;
    private GrilleExamenDTO idGrilleExamenDTO;
    private double notes;
    private String nom;
    private String matricule;
    private String anonimat;
    private String typeEval;
    private String niveau;
    private String UE;
    private String filiere;
    private String anneAcademique;
    private String session;
    private int semestre;
    private int ExamenNoteSur;
    private double credit;

}