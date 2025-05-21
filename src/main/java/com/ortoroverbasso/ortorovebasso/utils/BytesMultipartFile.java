package com.ortoroverbasso.ortorovebasso.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class BytesMultipartFile implements MultipartFile {

    private final byte[] content;
    private final String name;
    private final String contentType;

    public BytesMultipartFile(String name, byte[] content) {
        this(name, content, "image/jpeg");
    }

    public BytesMultipartFile(String name, byte[] content, String contentType) {
        this.name = name;
        this.content = content;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return name;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(java.io.File dest) throws IOException {
        throw new UnsupportedOperationException("transferTo not supported");
    }
}
