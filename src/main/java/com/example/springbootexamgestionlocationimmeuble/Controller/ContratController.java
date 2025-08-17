package com.example.springbootexamgestionlocationimmeuble.Controller;

import com.example.springbootexamgestionlocationimmeuble.DAO.ContratService;
import com.example.springbootexamgestionlocationimmeuble.DAO.UniteLocationService;
import com.example.springbootexamgestionlocationimmeuble.DAO.UtilisateurService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Contrat;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import com.example.springbootexamgestionlocationimmeuble.repository.ContratRepository;
import com.example.springbootexamgestionlocationimmeuble.repository.UtilisateurRepository;
import com.example.springbootexamgestionlocationimmeuble.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/contrats")
public class ContratController {

    @Autowired
    private ContratService contratService;

    @Autowired
    private UtilisateurService locataireService;

    @Autowired
    private UniteLocationService uniteLocationService;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    UtilisateurRepository contratRepository;

    @GetMapping("/admin/list")
    public String listeContrats(Model model) {

        model.addAttribute("contrats", contratService.getAllContrats());
        return "admin/listcontrat";
    }


    @GetMapping("/proprietaire/list")
    public String listeContratsProprietaire(Model model, Principal principal) {
        Optional<Utilisateur> proprietaire = contratRepository.findByEmail(principal.getName());
        List<Contrat> contrats = contratService.findByLocataire(proprietaire);
        model.addAttribute("contrats", contrats);
        return "proprietaire/listcontrat";
    }

    @GetMapping("/locataire/list")
    public String listeContratsLocataire(Model model, Principal principal) {
        // 1. Récupérer l'utilisateur connecté (locataire)
        Utilisateur locataire = contratRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // 2. Récupérer la liste des contrats associés à ce locataire
        List<Contrat> contrats = contratService.findByLocataire(locataire);

        // 3. Ajouter la liste des contrats au modèle pour la vue
        model.addAttribute("contrats", contrats);

        // 4. Retourner la vue Thymeleaf adaptée
        return "locataire/listcontrat";
    }



    @GetMapping("/proprietaire/add")
    public String formAjoutContrat(Model model) {
        model.addAttribute("contrat", new Contrat());
        model.addAttribute("locataires", locataireService.getAllUtilisateur());
        model.addAttribute("unites", uniteLocationService.getUnitesDisponibles());
        return "proprietaire/addcontrat";
    }

    @PostMapping("/save")
    public String enregistrerContrat(@ModelAttribute Contrat contrat, RedirectAttributes redirect) {
        contratService.saveContrat(contrat);
        redirect.addFlashAttribute("success", "Contrat ajouté avec succès !");
        return "redirect:/contrats/proprietaire/list";
    }

}
