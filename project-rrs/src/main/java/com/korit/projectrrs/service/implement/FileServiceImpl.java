package com.korit.projectrrs.service.implement;

import com.korit.projectrrs.common.ResponseMessage;
import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Value("${file.upload-dir}")
    private String rootPath;


    @Override
    public String uploadFile(MultipartFile file, String path) {

        if (file == null || file.isEmpty()) return null;

        String newFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = "file/" + path + "/" + newFileName;

        Path uploadDir = Paths.get(rootPath, "file", path);
        Path uploadPath = Paths.get(rootPath, filePath);

        try {
            createDirectoriesIfNoExists(uploadDir);

            Files.write(uploadPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePath;
    }

    private void createDirectoriesIfNoExists(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }

    @Override
    public void removeFile(String filePath) {
        String srcFileName = null;
        File file = new File(rootPath, filePath);

        if (file.exists()) {
            boolean deleted = file.delete();
        }
    }
}