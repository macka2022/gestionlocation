package com.example.springbootexamgestionlocationimmeuble.Controller;

import com.example.springbootexamgestionlocationimmeuble.DAO.StatiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
@Controller
@RequestMapping("/statistiques")
public class StatiqueController {



        @Autowired
        private StatiqueService statistiqueService;

        @GetMapping("/dashboard")
        public String afficherStatistiques(Model model) {
            Map<String, Long> paiementsParStatut = statistiqueService.countPaiementsParStatut();
            Map<String, Double> totalParMois = statistiqueService.totalPaiementsParMois();

            model.addAttribute("paiementsParStatut", paiementsParStatut);
            model.addAttribute("totalParMois", totalParMois);

            return "admin/dashboard";
        }
    }


