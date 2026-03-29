package sn.dev.ct.immosen.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.dev.ct.immosen.dto.PhotoDTO;
import sn.dev.ct.immosen.entity.Bien;
import sn.dev.ct.immosen.entity.Photo;
import sn.dev.ct.immosen.exception.ResourceNotFoundException;
import sn.dev.ct.immosen.mapper.PhotoMapper;
import sn.dev.ct.immosen.repository.BienRepository;
import sn.dev.ct.immosen.repository.PhotoRepository;
import sn.dev.ct.immosen.service.ImageStorageService;
import sn.dev.ct.immosen.service.PhotoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final String uploadDir = "/images";
    private final PhotoRepository photoRepository;
    private final BienRepository bienRepository;
    private final ImageStorageService imageStorageService;

    public PhotoServiceImpl(PhotoRepository photoRepository, BienRepository bienRepository, ImageStorageService imageStorageService) {
        this.photoRepository = photoRepository;
        this.bienRepository = bienRepository;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public String uploadPhotoBien(Long bienId, MultipartFile file) {
        Bien bien = bienRepository.findById(bienId).orElseThrow(() -> new ResourceNotFoundException("Bien", bienId));

        String chemin = imageStorageService.uploadImage(file, "biens");

        Photo photo = Photo.builder()
                .bien(bien)
                .chemin(chemin)
                .build();
        photoRepository.save(photo);
        return chemin;
    }

    @Override
    public void uploadPhotosBien(Long bienId, MultipartFile[] files) {

        Bien bien = bienRepository.findById(bienId).orElseThrow(() -> new ResourceNotFoundException("Bien", bienId));

        Path folderPath = Paths.get(uploadDir, "biens");
        try{
            if(!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }
            for(MultipartFile file : files) {
                String fileName = UUID.randomUUID() + "_" +file.getOriginalFilename();
                Path filePath = folderPath.resolve(fileName);
                String chemin = "/images/biens/" + fileName;

                Photo photo = Photo.builder()
                        .chemin(chemin)
                        .bien(bien)
                        .build();

                photoRepository.save(photo);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement des fichiers" +e);
        }

    }

    @Override
    public String uploadPhotoUser(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + "profils/" + fileName);
        try{
            Files.copy(file.getInputStream(), path);
        }catch(IOException e){
            throw new RuntimeException("Erreur lors de l'ajout de l'image. " +e);
        }
        return fileName;
    }

    @Override
    public String uploadPieceIdentite(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + "pieces/" + fileName);
        try{
            Files.copy(file.getInputStream(), path);
        }catch(IOException e){
            throw new RuntimeException("Erreur lors de l'ajout de l'image. " +e);
        }
        return fileName;
    }

    @Override
    public List<PhotoDTO> getPhotosByBien(Long bienId) {
        return photoRepository.findByBienId(bienId)
                .stream().map(PhotoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<PhotoDTO> getPhotosByUtilisateur(Long utilisateurId) {
        return photoRepository.findByUtilisateurId(utilisateurId)
                .stream().map(PhotoMapper::toDto).collect(Collectors.toList());
    }
}
