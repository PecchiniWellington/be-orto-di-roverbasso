package com.ortoroverbasso.ortorovebasso.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

  @Schema(description = "ID generato dal sistema", example = "1")
  private Long id;

  @Schema(description = "Nome completo dell'utente", example = "Francesco Rossi")
  private String name;

  @Schema(description = "Email dell'utente", example = "francesco@email.com")
  private String email;

}
