package com.ortoroverbasso.ortorovebasso.enums;

public enum ImageExtension {
    JPG("image/jpeg", ".jpg"),
    PNG("image/png", ".png"),
    WEBP("image/webp", ".webp"),
    GIF("image/gif", ".gif"),
    BMP("image/bmp", ".bmp"),
    TIFF("image/tiff", ".tiff"),
    DEFAULT("", ".jpg");

    private final String mimeType;
    private final String extension;

    ImageExtension(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public static String getExtensionForMimeType(String mimeType) {
        for (ImageExtension ie : values()) {
            if (ie.mimeType.equalsIgnoreCase(mimeType)) {
                return ie.extension;
            }
        }
        return DEFAULT.extension;
    }
}
