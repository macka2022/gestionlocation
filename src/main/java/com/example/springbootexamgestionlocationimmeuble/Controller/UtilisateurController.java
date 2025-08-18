package com.example.springbootexamgestionlocationimmeuble.Controller;


import com.example.springbootexamgestionlocationimmeuble.DAO.UtilisateurService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/immobilier/utilisateur")
public class UtilisateurController {
private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/add")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "admin/inscriptionUser"; // sans 'main'
    }

    @GetMapping("/list")
    public String afficherListeUtilisateur(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateur());
        return "/admin/listeUser";
        // sans 'main'
    }



}


