package sn.dev.ct.immosen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.dev.ct.immosen.entity.enums.StatutBien;
import sn.dev.ct.immosen.entity.enums.TypeBien;

import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "biens")
public class Bien extends BaseEntity{

    private String name;
    private String reference;
    @Enumerated(EnumType.STRING)
    private TypeBien type;
    private String adresse;
    private Double superficie;
    private Integer nombrePieces;
    @Column(nullable = false)
    private Double prix;

    @Enumerated(EnumType.STRING)
    private StatutBien statut = StatutBien.DISPONIBLE;
    @Column(nullable = false)
    private String description;

    // Propriétaire
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietaire_id")
    private Utilisateur proprietaire;

    // Photos du bien
    @OneToMany(mappedBy = "bien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    // Contrats liés
    @OneToMany(mappedBy = "bien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrat> contrats = new ArrayList<>();
    public Bien() {}
}
