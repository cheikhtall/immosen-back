package sn.dev.ct.immosen.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.dev.ct.immosen.dto.MensualiteDTO;
import sn.dev.ct.immosen.entity.Contrat;
import sn.dev.ct.immosen.exception.ResourceNotFoundException;
import sn.dev.ct.immosen.repository.ContratRepository;
import sn.dev.ct.immosen.service.MensualiteService;

import java.util.List;

@RestController
@RequestMapping("/mensualites")
@RequiredArgsConstructor
public class MensualiteController {

    private final MensualiteService mensualiteService;
    private final ContratRepository contratRepository;

    @GetMapping("/contrat/{contratId}")
    public ResponseEntity<List<MensualiteDTO>> getByContrat(@PathVariable Long contratId) {
        return ResponseEntity.ok(mensualiteService.getByContrat(contratId));
    }

    @PostMapping("/generer/{contratId}")
    public ResponseEntity<String> generer(@PathVariable Long contratId) {
        Contrat contrat = contratRepository.findById(contratId).orElseThrow(
                () -> new ResourceNotFoundException("Contrat", contratId)
        );
        mensualiteService.genererMensualite(contrat);
        return ResponseEntity.ok("Mensualités générées");
    }
}