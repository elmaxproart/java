package Sea.incubator.sgdeb.model;

import Sea.incubator.sgdeb.external.Etudiant;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Component
@Entity
@Table(name = "paiementInscrit")
public class PayementI {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long payementId;

        @Column(unique = true)
        private long codeBank;

        @Column(unique = true)
        private  String matriculeEtu;

        private String banque;
        private BigDecimal montant;

        @Enumerated(EnumType.STRING)
        private TypeTranche typeTranche;

        private String nomEtudiant;
        private LocalDateTime datecreation;
        private String anneeAcademique;





}
