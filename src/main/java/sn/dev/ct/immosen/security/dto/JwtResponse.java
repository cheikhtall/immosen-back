package sn.dev.ct.immosen.security.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String nom;
    private String prenom;
    boolean enabled;
    private List<String> roles;

    public JwtResponse(String token, Long id, String username, String email, String nom,String prenom, boolean enabled, List<String> roles){
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.enabled = enabled;
        this.roles = roles;
    }
}
