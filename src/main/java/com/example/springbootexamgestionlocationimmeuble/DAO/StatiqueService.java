package com.example.springbootexamgestionlocationimmeuble.DAO;

import java.util.Map;

public interface StatiqueService {
;

    // Exemple : compter le nombre de paiements par statut
    public Map<String, Long> countPaiementsParStatut() ;
    // Exemple : total des paiements par mois (format "YYYY-MM")
    public Map<String, Double> totalPaiementsParMois() ;

}
