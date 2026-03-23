package sn.dev.ct.immosen.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.dev.ct.immosen.service.ImageStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public String uploadImage(MultipartFile file, String folder) {
        try {
            String fileName = UUID.randomUUID() + "_" +file.getOriginalFilename();
            Path folderPath = Paths.get(uploadDir, folder);
            if(!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }
            Path filePath = folderPath.resolve(fileName);

            Files.write(filePath, file.getBytes());
            return "/" + uploadDir + "/" + folder + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier" +e);
        }
    }

}
