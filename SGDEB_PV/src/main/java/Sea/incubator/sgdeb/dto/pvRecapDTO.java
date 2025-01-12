package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.external.GrilleDTO;
import Sea.incubator.sgdeb.external.UEDTO;
import lombok.Data;

import java.util.*;

@Data
public class pvRecapDTO {

    private UUID id;
    private String matricule;
    private String nom;
    private String niveaux;
    private double mgp;
    private String Decision;
    private String mention;
    //RECUPERATION DE LA LISTE DE UE PAR LE RESTTEMPLATE
    private List<UEDTO> ueDTOs;
    //recuperstion de la grille
    private GrilleDTO grilleDTOs;
    //Recuperation de la liste des notes
    private Map<String, Double> notes = new HashMap<>();
    private ObservationDTO observationDTO;
}
