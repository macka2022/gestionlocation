package com.example.springbootexamgestionlocationimmeuble.security;

import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;

import com.example.springbootexamgestionlocationimmeuble.repository.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return new CustomUserDetails(utilisateur);
    }
}
