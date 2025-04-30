package com.ortoroverbasso.ortorovebasso.entity.user.authority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authorities") // Tabella nel database per AuthorityEntity
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID univoco per ogni AuthorityEntity

    private String roleName; // Nome del ruolo (es. "ROLE_USER", "ROLE_ADMIN", ecc.)

    // Costruttore vuoto (necessario per JPA)
    public AuthorityEntity() {
    }

    // Costruttore per inizializzare AuthorityEntity con roleName
    public AuthorityEntity(String roleName) {
        this.roleName = roleName;
    }

    // Getter e setter per id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter e setter per roleName
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "AuthorityEntity{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
