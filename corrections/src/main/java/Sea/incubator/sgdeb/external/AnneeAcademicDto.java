package Sea.incubator.sgdeb.external;

import lombok.Data;

import java.util.UUID;

@Data
public class AnneeAcademicDto {
    private UUID id;
    private int fin;
    private int debut;
    private String libelle;

    /**
     * Make libelle string.
     *
     * @return the string
     */
    public String makeLibelle(){
        if(this.fin>this.debut){
            this.libelle=this.debut+"/"+this.fin;
            return this.libelle;}
        else if(this.fin<this.debut){
            this.libelle=this.fin+"/"+this.debut;
            return this.libelle;
        }
        else
            this.libelle=this.debut+"/"+(this.fin+1);
        return this.libelle;
    }

}
