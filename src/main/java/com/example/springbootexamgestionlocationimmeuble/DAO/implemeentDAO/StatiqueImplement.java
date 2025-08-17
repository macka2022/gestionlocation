package com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO;

import com.example.springbootexamgestionlocationimmeuble.DAO.StatiqueService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Paiement;
import com.example.springbootexamgestionlocationimmeuble.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class StatiqueImplement implements StatiqueService {
    @Autowired
    PaiementRepository paiementRepository;
    @Override
    public Map<String, Long> countPaiementsParStatut() {
        List<Paiement> paiements = paiementRepository.findAll();

        return paiements.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getStatut() != null ? p.getStatut().name() : "INCONNU",
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Double> totalPaiementsParMois() {
        List<Paiement> paiements = paiementRepository.findAll();

        return paiements.stream()
                .filter(p -> p.getMois() != null)
                .collect(Collectors.groupingBy(
                        Paiement::getMois,
                        Collectors.summingDouble(Paiement::getMontant)
                ));
    }
}
