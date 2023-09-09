package com.ap.homebanking.services;

import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.ClientLoan;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDTO>getClientsDto();
    Client getClientCurrent(Authentication authentication);
    void saveClient(Client client);
    Client getClientByEmail(String email);
}
