package Sea.incubator.sgdeb.model;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "pvue")
public class PvUE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matricule;

    private String nomPrenom;
    private String niveau;

    @Column(name = "`ANO_CC`")
    private String ANO_CC;

    private double noteCCsur;

    @Column(name = "`ANO_EP`")
    private String ANO_EP;

    private double noteEPsur;

    @Column(name = "`ANO_EE`")
    private String ANO_EE;

    private double noteEEsur;
    private double noteTotaleSur;

    @Column(name = "`DEC`")
    private String DEC;

    private String mention;


    private int num;
    private String codeUE;
    private String TypeEval;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    private LocalDateTime delectAt;


}