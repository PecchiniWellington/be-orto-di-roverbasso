package com.ortoroverbasso.ortorovebasso.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/* Proteggere le rotte validando i parametri di query o di percorso per evitare che un utente malintenzionato inserisca dati dannosi */
public class ProfileController {
    @GetMapping("/profile")
    public ResponseEntity<Profile> getProfile(@RequestParam String username) {
        if (username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("Invalid username length");
        }
        // Recupera e ritorna il profilo
        /* return ResponseEntity.ok(profileService.getProfile(username)); */
        return ResponseEntity.ok(null);
    }
}
