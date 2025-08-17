package com.example.springbootexamgestionlocationimmeuble.repository;

import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByEmail(String email);
    long countByRole(Utilisateur.Role role);
}
