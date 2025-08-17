package com.example.springbootexamgestionlocationimmeuble.Controller;

import com.example.springbootexamgestionlocationimmeuble.DAO.DemandLocationService;
import com.example.springbootexamgestionlocationimmeuble.DAO.UniteLocationService;
import com.example.springbootexamgestionlocationimmeuble.DAO.UtilisateurService;
import com.example.springbootexamgestionlocationimmeuble.Entity.DemandLocation;
import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;

import com.example.springbootexamgestionlocationimmeuble.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/demandes")
public class DemandLocationController {

    @Autowired
    private DemandLocationService demandLocationService;

    @Autowired
    private UniteLocationService uniteLocationService;

    @Autowired
    private UtilisateurService utilisateurService;

    // 🔐 Récupérer l'utilisateur connecté
    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getUtilisateur(); // ✅ récupère l'utilisateur depuis CustomUserDetails
        }

        throw new IllegalStateException("Utilisateur non connecté ou principal invalide");
    }

    // 👤 1. Liste des demandes du locataire connecté
    @GetMapping("/mes-demandes")
    public String mesDemandes(Model model) {
        Utilisateur locataire = getCurrentUser();
        List<DemandLocation> mesDemandes = demandLocationService.getDemandesByLocataire(locataire);
        model.addAttribute("demandes", mesDemandes);
        return "locataire/mes_demandes";
    }

    // 🏠 2. Formulaire pour créer une nouvelle demande
    /*@GetMapping("/nouvelle/{uniteId}")
    public String formulaireNouvelleDemande(@PathVariable int uniteId, Model model) {
        UniteLocation unite = uniteLocationService.getUniteById(uniteId);
        DemandLocation demande = new DemandLocation();
        demande.setUnite(unite);
        model.addAttribute("demande", demande);
        return "demande/form";
    }*/

    // ✅ 3. Soumettre une demande
    @GetMapping("/nouvelle/{uniteId}")
    public String creerDemande(@PathVariable int uniteId) {
        Utilisateur locataire = getCurrentUser();
        UniteLocation unite = uniteLocationService.getUniteById(uniteId);

        if (unite == null) {
            return "redirect:/unites?error=unite_introuvable";
        }

        // ✅ Modifier directement le statut de l’unité existante
        unite.setStatut(UniteLocation.Statut.OCCUPE);
        uniteLocationService.saveUnite(unite); // ← Assure-toi que cette méthode existe

        // Création de la demande
        DemandLocation demande = new DemandLocation();
        demande.setUnite(unite);
        demande.setLocataire(locataire);
        demande.setDateDemande(LocalDate.now());
        demande.setStatut(DemandLocation.Statut.EN_ATTENTE);

        demandLocationService.saveDemande(demande);
        return "redirect:/demandes/mes-demandes";
    }


    // 🔧 4. Admin : Voir toutes les demandes
    @GetMapping("/admin")
    public String toutesLesDemandes(Model model) {
        List<DemandLocation> toutes = demandLocationService.getAllDemandes();
        model.addAttribute("demandes", toutes);
        return "admin/admin_liste_demande";
    }

    // ✅ 5. Admin : Changer le statut
    @PostMapping("/changer-statut/{id}")
    public String changerStatut(@PathVariable int id, @RequestParam("statut") String statut) {
        DemandLocation.Statut nouveauStatut = DemandLocation.Statut.valueOf(statut.toUpperCase());
        demandLocationService.changerStatut(id, nouveauStatut);
        return "redirect:/demandes/admin";
    }
}
