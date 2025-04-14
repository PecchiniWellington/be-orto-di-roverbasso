package com.ortoroverbasso.ortorovebasso.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

  // Costruttore vuoto
  public UserRequestDto() {
  }

  // Costruttore con tutti i campi
  public UserRequestDto(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  // Getter e Setter
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
