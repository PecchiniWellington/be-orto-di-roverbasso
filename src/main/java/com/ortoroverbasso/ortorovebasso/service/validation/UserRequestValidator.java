/*
 * package com.ortoroverbasso.ortorovebasso.service.validation;
 * 
 * import java.util.HashMap;
 * import java.util.Map;
 * 
 * import com.ortoroverbasso.ortorovebasso.dto.User.UserRequestDto;
 * import
 * com.ortoroverbasso.ortorovebasso.exception.MultiFieldValidationException;
 * 
 * public class UserRequestValidator {
 * 
 * public static void validate(UserRequestDto dto) {
 * Map<String, String> errors = new HashMap<>();
 * 
 * if (dto.getName() == null || dto.getName().isBlank()) {
 * errors.put("name", "Il nome è obbligatorio");
 * } else if (dto.getName().length() < 2 || dto.getName().length() > 50) {
 * errors.put("name", "Il nome deve avere tra 2 e 50 caratteri");
 * }
 * 
 * if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
 * errors.put("email", "Email non valida");
 * }
 * 
 * if (dto.getPassword() == null || dto.getPassword().length() < 6) {
 * errors.put("password", "La password deve avere almeno 6 caratteri");
 * }
 * 
 * if (!errors.isEmpty()) {
 * throw new MultiFieldValidationException(errors);
 * }
 * }
 * }
 */
