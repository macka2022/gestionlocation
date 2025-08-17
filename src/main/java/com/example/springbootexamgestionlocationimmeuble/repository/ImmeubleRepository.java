package com.example.springbootexamgestionlocationimmeuble.repository;

import com.example.springbootexamgestionlocationimmeuble.Entity.Immeuble;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImmeubleRepository extends JpaRepository<Immeuble, Integer> {
    List<Immeuble> findByProprietaire(Utilisateur proprietaire);
}
