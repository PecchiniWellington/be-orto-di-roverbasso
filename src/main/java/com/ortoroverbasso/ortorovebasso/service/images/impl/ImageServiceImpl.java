package com.ortoroverbasso.ortorovebasso.service.images.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesService;
import com.ortoroverbasso.ortorovebasso.utils.FileUtils;

@Service
public class ImageServiceImpl implements IImagesService {

    @Value("${BLOB_READ_WRITE_TOKEN}")
    private String API_KEY;
    @Value("${app.upload.max-file-size-bytes}")
    private long maxFileSize;

    @Override
    public ImagesResponseDto uploadImage(MultipartFile file, ImagesDetailDto requestDto) {
        try {
            if (file.getSize() > maxFileSize) {
                throw new IllegalArgumentException(
                        "Il file supera la dimensione massima consentita di " + (maxFileSize / 1024 / 1024) + "MB.");
            }

            HttpClient client = HttpClient.newHttpClient();
            InputStream inputStream = file.getInputStream();

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

                requestDto.setName(filename);
                requestDto.setUrl(url);

                ImagesResponseDto responseDto = new ImagesResponseDto();
                responseDto.setId(0);
                responseDto.setImages(List.of(requestDto));

                System.out.println("File caricato con successo: " + url);
                return responseDto;
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
}
