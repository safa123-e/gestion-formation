package com.demo.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.google.common.hash.Hashing;
import com.google.common.base.Charsets;

public class Sha512PasswordEncoder implements PasswordEncoder {

    // Cette méthode encode le mot de passe en utilisant SHA-512.
    @Override
    public String encode(CharSequence rawPassword) {
        return Hashing.sha512()
                      .hashString(rawPassword, Charsets.UTF_16)
                      .toString();
    }

    // Cette méthode vérifie si le mot de passe encodé correspond au mot de passe en texte brut.
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // On applique le hachage sur le mot de passe brut et on compare avec le mot de passe encodé.
        return encode(rawPassword).equals(encodedPassword);
    }

    // Vous pouvez éventuellement surcharger `upgradeEncoding()` si vous souhaitez
    // implémenter une logique de mise à jour du hachage pour les mots de passe existants.
    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;  // Aucune mise à jour nécessaire pour SHA-512 ici.
    }
}
