package sn.dev.ct.immosen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.dev.ct.immosen.entity.enums.StatutMensualite;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "mensualites")
public class Mensualite extends BaseEntity {

    private Integer mois;
    private Integer annee;
    private Double montantDu;
    private Double montantPaye;
    private LocalDate dateEcheance;
    @Enumerated(EnumType.STRING)
    private StatutMensualite statut = StatutMensualite.EN_ATTENTE;
    private Double penalite = 0.0;
    private LocalDate derniereNotification;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrat_id")
    private Contrat contrat;
    @OneToMany(mappedBy = "mensualite", cascade = CascadeType.ALL)
    private List<Paiement> paiements = new ArrayList<>();

    public Mensualite() {}
}
