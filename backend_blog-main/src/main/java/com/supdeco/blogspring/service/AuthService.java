package com.supdeco.blogspring.service;

import com.supdeco.blogspring.model.Utilisateur;
import com.supdeco.blogspring.register.LoginRequest;
import com.supdeco.blogspring.register.RegisterRequest;
import com.supdeco.blogspring.repository.UtilisateurRepository;
import com.supdeco.blogspring.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setFirstName(registerRequest.getFirstname());
        utilisateur.setLastName(registerRequest.getLastname());
        utilisateur.setUserName(registerRequest.getUsername());
        utilisateur.setEmail(registerRequest.getEmail());
        utilisateur.setPassword(encodePassword(registerRequest.getPassword()));

        utilisateurRepository.save(utilisateur);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
