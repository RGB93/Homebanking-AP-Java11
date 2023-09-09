package com.ap.homebanking.services.implement;

import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.services.ClientService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientServiceImplement implements ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Override
    public List<ClientDTO> getClientsDto() {
        return null;
    }

    @Override
    public Client getClientCurrent(Authentication authentication) {
        return null;
    }

    @Override
    public void saveClient(Client client) {

    }

    @Override
    public Client getClientByEmail(String email) {
        return null;
    }
}
