package sn.dev.ct.immosen.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.dev.ct.immosen.dto.UtilisateurDTO;
import sn.dev.ct.immosen.service.UtilisateurService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getAll() {
        return ResponseEntity.ok(utilisateurService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUser(@PathVariable Long id) {
        UtilisateurDTO dto = utilisateurService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<UtilisateurDTO> create(@Valid @RequestBody UtilisateurDTO dto) {
        return ResponseEntity.ok(utilisateurService.create(dto));
    }
}
