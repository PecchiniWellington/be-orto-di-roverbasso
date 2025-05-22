package com.ortoroverbasso.ortorovebasso.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class ImageExtensionUtil {
    public static String extractBaseNameFromUrl(String url) {
        try {
            URL u = new URL(url);
            String path = u.getPath();
            String filename = path.substring(path.lastIndexOf('/') + 1);
            int dotIndex = filename.lastIndexOf('.');
            return dotIndex > 0 ? filename.substring(0, dotIndex) : filename;
        } catch (MalformedURLException e) {
            return "downloaded-image";
        }
    }

    public static String extractExtensionFromUrl(String url) {
        try {
            URL u = new URL(url);
            String path = u.getPath();
            int lastDot = path.lastIndexOf('.');
            if (lastDot > 0 && lastDot < path.length() - 1) {
                return path.substring(lastDot);
            }
            return ".jpg"; // fallback
        } catch (MalformedURLException e) {
            return ".jpg";
        }
    }

    public static String buildFileName(String customName, String originalName, String forcedExtension) {
        // Estrai baseName da customName se presente, altrimenti da originalName
        String baseName = null;
        if (customName != null && !customName.isBlank()) {
            int dotIndex = customName.lastIndexOf('.');
            baseName = dotIndex > 0 ? customName.substring(0, dotIndex) : customName;
        } else if (originalName != null && !originalName.isBlank()) {
            int dotIndex = originalName.lastIndexOf('.');
            baseName = dotIndex > 0 ? originalName.substring(0, dotIndex) : originalName;
        }

        if (baseName == null || baseName.isBlank()) {
            baseName = "file";
        }

        // Se forcedExtension è fornita, usala; altrimenti estrai estensione da
        // customName o originalName
        String extension = null;
        if (forcedExtension != null && !forcedExtension.isBlank()) {
            extension = forcedExtension.startsWith(".") ? forcedExtension : "." + forcedExtension;
        } else {
            // Prova a estrarre estensione da customName prima, altrimenti da originalName
            extension = extractExtension(customName);
            if (extension == null) {
                extension = extractExtension(originalName);
            }
            if (extension == null) {
                extension = ".bin"; // fallback generico
            }
        }

        return baseName + extension;
    }

    private static String extractExtension(String filename) {
        if (filename == null || filename.isBlank())
            return null;
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0 && lastDot < filename.length() - 1) {
            return filename.substring(lastDot);
        }
        return null;
    }

}
