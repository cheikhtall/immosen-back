package sn.dev.ct.immosen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.dev.ct.immosen.entity.enums.ModePaiement;

import java.time.LocalDateTime;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "paiements")
public class Paiement extends BaseEntity {

    private LocalDateTime datePaiement;
    private Double montant;
    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;
    private String referencePaiement;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mensualite_id")
    private Mensualite mensualite;

    public Paiement() {}
}
