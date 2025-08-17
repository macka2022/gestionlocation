package com.example.springbootexamgestionlocationimmeuble.Controller;

import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {

        return "login"; // nom du fichier Thymeleaf : login.html
    }
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "inscriptionUser";
    }

   @GetMapping("/login/connect")
    public String redirectByRole(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "redirect:statistique/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("LOCATAIRE"))) {
            return "redirect:immobilier/immeubles/locataire/list";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("PROPRIETAIRE"))) {
            return "redirect:immobilier/immeubles/proprietaire/list";
        } else {
//            return "redirect:/access-denied";
            return "redirect:/immobilier/unitelocation/list";
        }
    }
}
