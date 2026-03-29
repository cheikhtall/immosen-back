package sn.dev.ct.immosen.api;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.dev.ct.immosen.dto.PhotoDTO;
import sn.dev.ct.immosen.service.PhotoService;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class ImageController {

    private final PhotoService photoService;

    // 🔥 Upload photo pour un bien (une seule)
    @PostMapping("/bien/{bienId}")
    public ResponseEntity<String> uploadPhotoBien(
            @PathVariable Long bienId,
            @RequestParam("file") MultipartFile file) {

        String fileName = photoService.uploadPhotoBien(bienId, file);
        return ResponseEntity.ok(fileName);
    }

    // 🔥 Upload plusieurs photos pour un bien
    @PostMapping("/bien/{bienId}/multiple")
    public ResponseEntity<String> uploadPhotosBien(
            @PathVariable Long bienId,
            @RequestParam("files") MultipartFile[] files) {

        photoService.uploadPhotosBien(bienId, files);
        return ResponseEntity.ok("Photos ajoutées avec succès");
    }

    // 🔥 Upload photo profil utilisateur
    @PostMapping("/utilisateur/profil")
    public ResponseEntity<String> uploadPhotoUser(
            @RequestParam("file") MultipartFile file) {

        String fileName = photoService.uploadPhotoUser(file);
        return ResponseEntity.ok(fileName);
    }

    // 🔥 Upload pièce d'identité
    @PostMapping("/utilisateur/piece-identite")
    public ResponseEntity<String> uploadPieceIdentite(
            @RequestParam("file") MultipartFile file) {

        String fileName = photoService.uploadPieceIdentite(file);
        return ResponseEntity.ok(fileName);
    }

    // 🔥 Récupérer photos d’un bien
    @GetMapping("/bien/{bienId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByBien(
            @PathVariable Long bienId) {

        return ResponseEntity.ok(photoService.getPhotosByBien(bienId));
    }

    // 🔥 Récupérer photos d’un utilisateur
    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByUtilisateur(
            @PathVariable Long utilisateurId) {

        return ResponseEntity.ok(photoService.getPhotosByUtilisateur(utilisateurId));
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws MalformedURLException {
        Path path = Paths.get("images").resolve(filename);
        Resource resource = new UrlResource(path.toUri());

        if(!resource.exists()) {
            throw new RuntimeException("Fichier avec le nom " + filename + " n'existe pas");
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(resource.getFilename()))
                .body(resource);

        /*return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);*/
    }
}