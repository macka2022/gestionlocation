package com.example.springbootexamgestionlocationimmeuble.security;

//import com.example.springbootexamgestionlocationimmeuble.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final com.example.springbootexamgestionlocationimmeuble.security.CustomUserDetailsService userDetailsService;

    public SecurityConfig(com.example.springbootexamgestionlocationimmeuble.security.CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")  // action du formulaire
                        .usernameParameter("email")   // ⚠ c'est ici qu'on change "username" en "email"
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                                    // Redirection selon rôle
                                    if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                                        response.sendRedirect("/admin/dashboard");
                                    } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LOCATAIRE"))) {
                                        response.sendRedirect("/immobilier/immeuble/locataire/list");
                                    } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROPRIETAIRE"))) {
                                        response.sendRedirect("/immobilier/immeuble/proprietaire/list");
                                    } else {
                                        response.sendRedirect("/access-denied");
                                    }
                        })
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

  /*  @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // ton service qui charge par email
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
