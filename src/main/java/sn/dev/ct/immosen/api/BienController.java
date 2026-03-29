package sn.dev.ct.immosen.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.dev.ct.immosen.dto.BienDTO;
import sn.dev.ct.immosen.dto.PhotoDTO;
import sn.dev.ct.immosen.service.BienService;
import sn.dev.ct.immosen.service.PhotoService;

import java.util.List;

@RestController
@RequestMapping("/biens")
public class BienController {
    private final BienService bienService;
    private final PhotoService photoService;

    public BienController(BienService bienService, PhotoService photoService) {
        this.bienService = bienService;
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<BienDTO> create(@RequestBody BienDTO bienDTO) {
        return ResponseEntity.ok(bienService.create(bienDTO));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BienDTO> update(@PathVariable Long id,
                                          @RequestBody BienDTO dto) {
        return ResponseEntity.ok(bienService.update(dto, id));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BienDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bienService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BienDTO>> getAll() {
        return ResponseEntity.ok(bienService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        bienService.delete(id);
        return ResponseEntity.ok("Bien " + id + " supprimé");
    }


    @PostMapping("/{id}/photo")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        photoService.uploadPhotoBien(id, file);
        return ResponseEntity.ok("Image uploadée");
    }

    // 🔥 Upload plusieurs images
    @PostMapping("/{id}/photos")
    public ResponseEntity<String> uploadPhotos(
            @PathVariable Long id,
            @RequestParam("files") MultipartFile[] files) {

        photoService.uploadPhotosBien(id, files);
        return ResponseEntity.ok("Images uploadées");
    }

    // 🔥 Récupérer toutes les images d’un bien
    @GetMapping("/{id}/photos")
    public ResponseEntity<List<PhotoDTO>> getPhotos(@PathVariable Long id) {
        return ResponseEntity.ok(photoService.getPhotosByBien(id));
    }

    // 🔥 Récupérer image principale
    /*@GetMapping("/{id}/photo-principale")
    public ResponseEntity<PhotoDTO> getPhotoPrincipale(@PathVariable Long id) {
        return ResponseEntity.ok(photoService.getPhotoPrincipale(id));
    }*/
}
