package com.example.springbootexamgestionlocationimmeuble.repository;

import com.example.springbootexamgestionlocationimmeuble.Entity.DemandLocation;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandRepository extends JpaRepository<DemandLocation, Integer> {
    List<DemandLocation> getByLocataire(Utilisateur locataire);
}
