package sn.dev.ct.immosen.service;

import org.springframework.web.multipart.MultipartFile;
import sn.dev.ct.immosen.dto.PhotoDTO;

import java.util.List;

public interface PhotoService {
    String uploadPhotoBien(Long bienId, MultipartFile file);
    void uploadPhotosBien(Long bienId, MultipartFile[] files);
    String uploadPhotoUser(MultipartFile file);
    String uploadPieceIdentite(MultipartFile file);
    List<PhotoDTO> getPhotosByBien(Long bienId);
    List<PhotoDTO> getPhotosByUtilisateur(Long utilisateurId);
}
