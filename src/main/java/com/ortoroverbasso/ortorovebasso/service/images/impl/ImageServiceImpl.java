package com.ortoroverbasso.ortorovebasso.service.images.impl;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesService;

@Service
public class ImageServiceImpl implements IImagesService {

    @Override
    public ImagesResponseDto uploadImage(String imagePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadImage'");
    }

    @Override
    public byte[] downloadImage(String imageId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'downloadImage'");
    }

    @Override
    public void deleteImage(Long imageId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteImage'");
    }

}
