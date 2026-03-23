package sn.dev.ct.immosen.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.dev.ct.immosen.entity.enums.TypePhoto;

@Entity
@Table(name = "photos")
@SuperBuilder @Getter
@Setter
public class Photo extends BaseEntity{

    private String nomFichier;
    private String chemin;
    @Enumerated(EnumType.STRING)
    private TypePhoto type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    @JsonIgnore
    private Utilisateur utilisateur;
    private Boolean estPrincipale;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_id")
    @JsonIgnore
    private Bien bien;
    public Photo(){}
}
