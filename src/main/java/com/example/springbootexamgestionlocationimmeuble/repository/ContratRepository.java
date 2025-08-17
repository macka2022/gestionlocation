package com.example.springbootexamgestionlocationimmeuble.repository;

import com.example.springbootexamgestionlocationimmeuble.Entity.Contrat;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface ContratRepository extends JpaRepository<Contrat, Integer> {
    List<Contrat> findByLocataire(Optional<Utilisateur> proprietaire);
    @Query("SELECT c FROM Contrat c WHERE c.uniteLocation.immeuble.proprietaire = :proprietaire")
    List<Contrat> findByProprietaire(@Param("proprietaire") Optional<Utilisateur> proprietaire);
    List<Contrat> findByLocataire(Utilisateur locataire);



    @Query("select count(c) from Contrat c where c.dateDebut <= :today and (c.dateFin is null or c.dateFin >= :today)")
    long countActive(@Param("today") LocalDate today);


}
