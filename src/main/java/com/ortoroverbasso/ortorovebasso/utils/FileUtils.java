package com.ortoroverbasso.ortorovebasso.utils;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    private static final Map<String, String> EXTENSION_MAP = Map.of(
            "jpeg", "jpg",
            "jpg", "jpg",
            "png", "png",
            "webp", "webp");

    public static String normalizeFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("Il file non ha un nome valido.");
        }

        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < originalFilename.length() - 1) {
            extension = originalFilename.substring(dotIndex + 1).toLowerCase();
        }

        String normalizedExt = EXTENSION_MAP.get(extension);
        if (normalizedExt == null) {
            throw new IllegalArgumentException("Estensione file non supportata: " + extension);
        }

        String baseName = originalFilename.substring(0, dotIndex)
                .replaceAll("[^a-zA-Z0-9_-]", "_");

        return baseName + "." + normalizedExt;
    }

    public static String getContentTypeFromExtension(String extension) {
        return switch (extension) {
            case "jpg" -> "image/jpeg";
            case "png" -> "image/png";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }
}
