package sn.dev.ct.immosen.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.dev.ct.immosen.dto.ContratDTO;
import sn.dev.ct.immosen.service.ContratService;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final ContratService contratService;

    @PostMapping
    public ResponseEntity<ContratDTO> create(@RequestBody ContratDTO dto) {
        return ResponseEntity.ok(contratService.create(dto));
    }

    @PutMapping("/{id}/resilier")
    public ResponseEntity<String> resilier(@PathVariable Long id) {
        contratService.resilierContrat(id);
        return ResponseEntity.ok("Contrat résilié");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contratService.getContratById(id));
    }
}