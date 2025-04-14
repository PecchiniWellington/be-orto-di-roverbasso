package com.ortoroverbasso.ortorovebasso.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

  @NotBlank(message = "Il nome è obbligatorio")
  @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
  @Schema(description = "Nome completo dell'utente", example = "Francesco Rossi")
  private String name;

  @NotBlank(message = "L'email è obbligatoria")
  @Email(message = "L'email deve essere valida")
  @Schema(description = "Email dell'utente", example = "francesco@email.com")
  private String email;

  @NotBlank(message = "La password è obbligatoria")
  @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
  @Schema(description = "Password in chiaro (da criptare)", example = "supersegreta")
  private String password;
}
