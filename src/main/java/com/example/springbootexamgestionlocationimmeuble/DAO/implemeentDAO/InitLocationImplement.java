package com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO;

import com.example.springbootexamgestionlocationimmeuble.DAO.UniteLocationService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Immeuble;
//import com.example.springbootexamgestionlocationimmeuble.Entity.Paiement;
import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;
import com.example.springbootexamgestionlocationimmeuble.repository.UniteLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class InitLocationImplement implements UniteLocationService {

    private final UniteLocationRepository uniteLocationService;
    private final UniteLocationRepository uniteLocationRepository;

    public InitLocationImplement(UniteLocationRepository uniteLocationService, UniteLocationRepository uniteLocationRepository) {
        this.uniteLocationService = uniteLocationService;
        this.uniteLocationRepository = uniteLocationRepository;
    }


    public List<UniteLocation> getUnitesAvecContrat() {
        return uniteLocationRepository.findUnitesWithContrats();
    }

    @Override
    public List<UniteLocation> getAllUnite() {
        return uniteLocationRepository.findAll();
    }

    @Override
    public UniteLocation getUniteById(int id) {
        return uniteLocationRepository.findById(id).get();
    }

    @Override
    public UniteLocation saveUnite(UniteLocation unite) {
        return uniteLocationRepository.save(unite);
    }

    @Override
    public List<UniteLocation> getUnitesByImmeuble(Immeuble immeuble) {
        return uniteLocationRepository.findByImmeuble(immeuble);
    }

    @Override
    public void deleteUnite(int id) {
        uniteLocationRepository.deleteById(id);
    }

    @Override
    public List<UniteLocation> getUnitesDisponibles() {
        return uniteLocationRepository.findByStatut(UniteLocation.Statut.DISPONIBLE);
    }

}
