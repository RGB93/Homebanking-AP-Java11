package com.ap.homebanking.controllers;

import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("You must write down your first name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()) {
            return new ResponseEntity<>("You must write down your last name", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()) {
            return new ResponseEntity<>("You must write down an email", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {
            return new ResponseEntity<>("You must write down a password", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
}
