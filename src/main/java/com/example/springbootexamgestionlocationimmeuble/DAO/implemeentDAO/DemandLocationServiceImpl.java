package com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO;



import com.example.springbootexamgestionlocationimmeuble.DAO.DemandLocationService;
import com.example.springbootexamgestionlocationimmeuble.Entity.DemandLocation;

import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import com.example.springbootexamgestionlocationimmeuble.repository.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandLocationServiceImpl implements DemandLocationService {

    @Autowired
    private DemandRepository repository;

    @Override
    public List<DemandLocation> getAllDemandes() {
        return repository.findAll();
    }

    @Override
    public DemandLocation getDemandeById(int id) {
        return repository.findById(id).orElse(null);
    }
    @Override
    public List<DemandLocation> getDemandesByLocataire(Utilisateur locataire) {
        return repository.getByLocataire(locataire);
    }
    @Override
    public DemandLocation saveDemande(DemandLocation demande) {
        return repository.save(demande);
    }

    @Override
    public void deleteDemande(int id) {
        repository.deleteById(id);
    }

    @Override
    public DemandLocation changerStatut(int id, DemandLocation.Statut statut) {
        DemandLocation demande = repository.findById(id).orElse(null);
        if (demande != null) {
            demande.setStatut(statut);
            return repository.save(demande);
        }
        return null;
    }
}
