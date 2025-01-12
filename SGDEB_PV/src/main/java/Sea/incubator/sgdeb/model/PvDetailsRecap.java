package Sea.incubator.sgdeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "details_pvRecap")
public class PvDetailsRecap {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String matricule;
        private String nom;
        private double mgp;
        private String niveau;
        @ManyToOne
        @JoinColumn(name = "pv_ue_id")
        private PvRecap pvRecap;


}
