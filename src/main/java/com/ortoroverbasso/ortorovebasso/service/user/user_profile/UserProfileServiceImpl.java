// UserProfileServiceImpl.java aggiornato
package com.ortoroverbasso.ortorovebasso.service.user.user_profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_profile.UserProfileEntity;
import com.ortoroverbasso.ortorovebasso.mapper.user.UserProfileMapper;
import com.ortoroverbasso.ortorovebasso.repository.images.ImagesDetailRepository;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.repository.user.user_profile.UserProfileRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;

@Service
public class UserProfileServiceImpl implements IUserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ImagesDetailRepository imagesDetailRepository;

    @Autowired
    private IImagesDetailService imagesService;

    @Override
    @Transactional
    public UserProfileResponseDto create(Long userId, UserProfileRequestDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileEntity profile = new UserProfileEntity();
        profile.setUser(user);
        UserProfileMapper.updateEntity(profile, dto, imagesDetailRepository);

        user.setProfile(profile);
        userProfileRepository.save(profile);

        return UserProfileMapper.toResponseDto(profile);
    }

    @Override
    @Transactional
    public UserProfileResponseDto update(Long userId, UserProfileRequestDto dto) {
        UserProfileEntity profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        UserProfileMapper.updateEntity(profile, dto, imagesDetailRepository);
        userProfileRepository.save(profile);

        return UserProfileMapper.toResponseDto(profile);
    }

    @Override
    public UserProfileResponseDto get(Long userId) {
        UserProfileEntity profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return UserProfileMapper.toResponseDto(profile);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        UserProfileEntity profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Rimuove l'avatar associato, se presente
        ImagesDetailEntity avatar = profile.getAvatar();
        if (avatar != null) {
            imagesDetailRepository.delete(avatar);
        }

        userProfileRepository.delete(profile);
    }

    @Override
    @Transactional
    public UserProfileResponseDto uploadAndSetAvatar(Long userId, MultipartFile file) {
        // 1. Trova utente e profilo
        UserProfileEntity profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profilo non trovato"));

        // 2. Upload immagine su B2 e salvataggio entità
        ImagesDetailEntity uploadedImage = imagesService.uploadAndReturnEntity(file);

        // 3. Elimina vecchio avatar se presente
        ImagesDetailEntity oldAvatar = profile.getAvatar();
        if (oldAvatar != null) {
            imagesDetailRepository.delete(oldAvatar);
        }

        // 4. Collega l’immagine al profilo
        profile.setAvatar(uploadedImage);
        userProfileRepository.save(profile);

        // 5. Ritorna il profilo aggiornato
        return UserProfileMapper.toResponseDto(profile);
    }
}
