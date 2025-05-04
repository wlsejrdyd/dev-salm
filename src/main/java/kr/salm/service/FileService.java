package kr.salm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<String> saveFiles(MultipartFile[] files) {
        List<String> savedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            try {
                // 디렉토리 없으면 생성
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 고유 파일명 생성
                String ext = getFileExtension(file.getOriginalFilename());
                String newFileName = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
                Path targetPath = Paths.get(uploadDir).resolve(newFileName);

                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                savedFiles.add(newFileName); // 저장된 파일명 리스트에 추가
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return savedFiles;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
