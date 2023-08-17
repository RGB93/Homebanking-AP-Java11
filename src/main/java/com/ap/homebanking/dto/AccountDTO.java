package com.ap.homebanking.dto;

import com.ap.homebanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;

    private Set <TransactionDTO> transactions;

    private AccountDTO (){
    }

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
    }
    public Long getId (){ return id;}
    public String getNumber(){return number;}
    public LocalDate getCreationDate(){return creationDate;}
    public Double getBalance(){return balance;}
    public Set<TransactionDTO> getTransactions(){return transactions;}
}
