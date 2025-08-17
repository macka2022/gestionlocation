package com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO;

import com.example.springbootexamgestionlocationimmeuble.DAO.ContratService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Contrat;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import com.example.springbootexamgestionlocationimmeuble.repository.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplemantContrat implements ContratService {
   @Autowired
    private ContratRepository ContratRepository;

   /* @Override
    public List<Contrat> findByLocataire(Optional<Utilisateur> proprietaire) {
        return ContratRepository.findByLocataire(proprietaire);
    }*/

    public List<Contrat> getAllContrats() {
        return ContratRepository.findAll();
    }
    public List<Contrat> findByLocataire(Optional<Utilisateur> proprietaire) {
        return ContratRepository.findByProprietaire(proprietaire);
    }

    @Override
    public List<Contrat> findByLocataire(Utilisateur locataire) {
        return ContratRepository.findByLocataire(locataire);
    }

    public Contrat getContratById(int id) {
        return ContratRepository.findById(id).orElse(null);
    }

    public Contrat saveContrat(Contrat contrat) {
        return ContratRepository.save(contrat);
    }

    public void deleteContrat(int id) {
        ContratRepository.deleteById(id);
    }
}
