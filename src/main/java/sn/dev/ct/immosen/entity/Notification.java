package sn.dev.ct.immosen.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "notifications")
public class Notification extends BaseEntity{

    private String message;
    private boolean lue = false;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    public Notification() {}
}
