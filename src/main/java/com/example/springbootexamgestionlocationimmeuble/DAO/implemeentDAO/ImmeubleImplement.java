package com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO;

import com.example.springbootexamgestionlocationimmeuble.DAO.ImeubleService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Immeuble;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import com.example.springbootexamgestionlocationimmeuble.repository.ImmeubleRepository;
import com.example.springbootexamgestionlocationimmeuble.repository.UniteLocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImmeubleImplement  implements ImeubleService {
    public ImmeubleImplement(ImmeubleRepository immeubleRepository, UniteLocationRepository uniteLocationRepository ) {
        this.immeubleRepository = immeubleRepository;
        this.uniteLocationRepository = uniteLocationRepository;

    }

    private  final ImmeubleRepository immeubleRepository;
    private final UniteLocationRepository uniteLocationRepository;
    @Override
    public List<Immeuble> getAllImmmeuble() {
        return immeubleRepository.findAll();
    }

    @Override
    public Immeuble getImmmeubleById(int id) {
        return immeubleRepository.findById(id).get();
    }

    @Override
    public void deleteImmeuble(int id) {
        immeubleRepository.deleteById(id);
    }

    @Override
    public Immeuble saveImmmeuble(Immeuble immmeuble) {
        return immeubleRepository.save(immmeuble);
    }

    @Override
    public List<Immeuble> getAllImmeubleByUser(Utilisateur utilisateur){
        return immeubleRepository.findByProprietaire(utilisateur);
    }


    public long countAll() {
        return immeubleRepository.count();
    }

    public long countAllUnits() {
        return uniteLocationRepository.count();
    }

    public long countOccupiedUnits() {
        return uniteLocationRepository.countByOccupied(true);
    }

    public long countAvailableUnits() {
        return uniteLocationRepository.countByOccupied(false);
    }

    public Map<String, long[]> getOccupancyStats() {
        return immeubleRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Immeuble::getNom,
                        i -> new long[]{
                                uniteLocationRepository.countByImmeubleAndOccupied(i, false), // disponibles
                                uniteLocationRepository.countByImmeubleAndOccupied(i, true)   // occupées
                        }
                ));
    }


}
