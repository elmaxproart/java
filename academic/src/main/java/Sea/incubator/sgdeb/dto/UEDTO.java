package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.model.enumType.Semestre;
import Sea.incubator.sgdeb.model.enumType.TypeUE;
import lombok.Data;

import java.util.UUID;


/**
 * The type Uedto.
 */
@Data
public class UEDTO {
    private UUID id;
    private String libelle;
    private String code;
    private int credits;
    private int volumeHoraire;
    private Semestre semestre;
    private TypeUE typeUE;
    private int pourcentageTp;
    private  int pourcentageCC;
    private int pourcentageEE;

    private GrilleDTO grille;


    /**
     * Make pourcentage.
     *
     * @param pourcentageTp the pourcentage tp
     * @param porcentageCC  the porcentage cc
     * @param pourcentageSN the pourcentage sn
     */
    public void makePourcentage(int pourcentageTp,int porcentageCC, int pourcentageSN){
        if(pourcentageTp==0){
            this.pourcentageCC =30;
            this.pourcentageEE =70;
        }
        else if((pourcentageTp+pourcentageSN+porcentageCC)==100){
            this.pourcentageCC =porcentageCC;
            this.pourcentageTp=pourcentageTp;
            this.pourcentageEE =pourcentageSN;
        }
        else{
            this.pourcentageCC=25;
            this.pourcentageEE =25;
            this.pourcentageTp=50;
        }
    }


}
