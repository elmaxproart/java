package Sea.incubator.sgdeb.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "details_pv")
public class PvDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String matricule;
    private String nom;
    private double note;
    private String niveau;
    @ManyToOne
    @JoinColumn(name = "pv_ue_id")
    private PvUE pvUE;
}
