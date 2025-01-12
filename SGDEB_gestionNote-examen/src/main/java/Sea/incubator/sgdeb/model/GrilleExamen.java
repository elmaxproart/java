package Sea.incubator.sgdeb.model;
import Sea.incubator.sgdeb.external.enumType.TypeEval;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@Table(name = "Examen")
public class GrilleExamen {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String codeUE;
    private String codeFiliere;
    @Enumerated(value = EnumType.STRING)
    private TypeEval typeEval;
    private String anneeAcademic;
    private int noteSur;
    private int statut;
    private String session;
    private int semestre;
    private int credit;
    private Date dateImportation;




    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime dateEnregistrement;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    private LocalDateTime delectAt; // Consider renaming to deleteAt for clarity


}
