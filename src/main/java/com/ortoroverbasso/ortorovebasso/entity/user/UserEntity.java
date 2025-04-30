package com.ortoroverbasso.ortorovebasso.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    // Costruttore vuoto
    public UserEntity() {
    }

    // Costruttore con email e password
    public UserEntity(String email, String password) {
        this.email = email != null ? email : "defaultEmail@example.com"; // Default email
        this.password = password != null ? password : "defaultPassword"; // Default password
    }

    // Costruttore con solo ID (aggiungi questo costruttore)
    public UserEntity(Long id) {
        this.id = id;
    }

    // Getter e setter per gli altri campi
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
