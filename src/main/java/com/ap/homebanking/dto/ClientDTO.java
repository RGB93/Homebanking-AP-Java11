package com.ap.homebanking.dto;

import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;

public class ClientDTO<toList> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<Account> accounts = new HashSet<>();
    public ClientDTO( Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();;
        this.email = client.getEmail();
        this.accounts = client.getAccounts();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

}
