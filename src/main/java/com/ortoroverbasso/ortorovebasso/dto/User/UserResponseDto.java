package com.ortoroverbasso.ortorovebasso.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserResponseDto {

  @Schema(description = "ID generato dal sistema", example = "1")
  private Long id;

  @Schema(description = "Nome completo dell'utente", example = "Francesco Rossi")
  private String name;

  @Schema(description = "Email dell'utente", example = "francesco@email.com")
  private String email;

  public UserResponseDto() {
  }

  public UserResponseDto(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
}
