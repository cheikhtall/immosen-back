package sn.dev.ct.immosen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.dev.ct.immosen.entity.enums.TypeContrat;
import sn.dev.ct.immosen.entity.enums.TypeDureeContrat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "contrats")
public class Contrat extends BaseEntity{

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double montantMensuel;
    private Double caution;
    @Enumerated(EnumType.STRING)
    private TypeContrat typeContrat;
    @Enumerated(EnumType.STRING)
    private TypeDureeContrat typeDureeContrat;
    private boolean actif = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locataire_id")
    private Utilisateur locataire;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_id")
    private Bien bien;
    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensualite> mensualites = new ArrayList<>();
    public Contrat() {}
}
