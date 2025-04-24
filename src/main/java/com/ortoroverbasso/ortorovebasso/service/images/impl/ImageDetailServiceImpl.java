package com.ortoroverbasso.ortorovebasso.service.images.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;
import com.ortoroverbasso.ortorovebasso.mapper.images.ImagesMapper;
import com.ortoroverbasso.ortorovebasso.repository.images.ImagesDetailRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.utils.FileUtils;

@Service
public class ImageDetailServiceImpl implements IImagesDetailService {

    @Value("${BLOB_READ_WRITE_TOKEN}")
    private String API_KEY;
    @Value("${app.upload.max-file-size-bytes}")
    private long maxFileSize;

    @Autowired
    private ImagesDetailRepository imagesDetailRepository;

    @Override
    public ImagesDetailDto uploadImage(MultipartFile file) {
        try {
            if (file.getSize() > maxFileSize) {
                throw new IllegalArgumentException(
                        "Il file supera la dimensione massima consentita di " + (maxFileSize / 1024 / 1024) + "MB.");
            }

            HttpClient client = HttpClient.newHttpClient();
            String filename = FileUtils.normalizeFilename(file);
            String extension = filename.substring(filename.lastIndexOf('.') + 1);
            String contentType = FileUtils.getContentTypeFromExtension(extension);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://blob.vercel-storage.com/upload?filename=" + filename))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", contentType)
                    .PUT(HttpRequest.BodyPublishers.ofInputStream(() -> {
                        try {
                            return file.getInputStream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> responseMap = mapper.readValue(response.body(), new TypeReference<>() {
                });
                String url = (String) responseMap.get("url");

                ImagesDetailEntity imageEntity = new ImagesDetailEntity();
                imageEntity.setName(filename);
                imageEntity.setUrl(url);
                ImagesDetailEntity savedImage = imagesDetailRepository.save(imageEntity);

                return ImagesMapper.toResponse(savedImage);
            } else {
                throw new RuntimeException("Upload fallito: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Errore durante l'upload", e);
        }
    }

    @Override
    public byte[] downloadImage(String filename) {
        try {
            String fileUrl = "https://dlkujg8sq6zn2a3i.public.blob.vercel-storage.com/" + filename;
            return new URL(fileUrl).openStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Errore nel download", e);
        }

    }

    @Override
    public void deleteImage(Long imageId) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://blob.vercel-storage.com/delete/" + imageId))
                    .header("Authorization", "Bearer " + API_KEY)
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("File eliminato con successo: " + imageId);
            } else {
                throw new RuntimeException("Eliminazione fallita: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Errore durante l'eliminazione", e);
        }
    }
}
