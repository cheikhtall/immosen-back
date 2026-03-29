package sn.dev.ct.immosen.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.dev.ct.immosen.service.PaiementService;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping("/simple")
    public ResponseEntity<String> payerUneMensualite(
            @RequestParam Long mensualiteId,
            @RequestParam Double montant,
            @RequestParam String modePaiement) {

        paiementService.payerMensualite(mensualiteId, montant, modePaiement);
        return ResponseEntity.ok("Paiement effectué");
    }

    @PostMapping("/multi")
    public ResponseEntity<String> payerPlusieurs(
            @RequestParam Long contratId,
            @RequestParam Double montant,
            @RequestParam String modePaiement) {

        paiementService.payerPlusieursMensualites(contratId, montant, modePaiement);
        return ResponseEntity.ok("Paiement réparti");
    }
}
