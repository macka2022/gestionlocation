package com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO;

import com.example.springbootexamgestionlocationimmeuble.DAO.UtilisateurService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import com.example.springbootexamgestionlocationimmeuble.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
@Service
public class UtilisateurImplement implements UtilisateurService {
private final UtilisateurRepository utilisateurRepository ;

    public UtilisateurImplement(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurRepository.findAll() ;
    }

    @Override
    public Utilisateur getUtilisateurById(int id) {
        return null;
    }

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {



            String hashedPassword = passwordEncoder.encode(utilisateur.getPassword());
            utilisateur.setPassword(hashedPassword);
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {

    }

    @Override
    public long countAll() {
        return utilisateurRepository.count();
    }

    @Override
    public long countByRole(Utilisateur.Role role) {
        return utilisateurRepository.countByRole(role);
    }
}
