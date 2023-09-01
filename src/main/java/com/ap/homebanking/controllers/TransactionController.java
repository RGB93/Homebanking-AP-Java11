package com.ap.homebanking.controllers;

import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.models.TransactionType;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    @RequestMapping(path = "/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> addTransaction(Authentication authentication, @RequestParam Double amount, @RequestParam String description, @RequestParam String accountOrigin, @RequestParam String accountDestination){
        Client client = clientRepository.findByEmail(authentication.getName());
        Client authenticated = clientRepository.findByEmail(authentication.getName());
        Account Originaccount = accountRepository.findByNumber(accountOrigin);
        Account DestAccount = accountRepository.findByNumber(accountDestination);
        //Verificar existencia de cuenta de origen
        if(!accountRepository.findByNumber(accountOrigin).equals(accountOrigin)){
            return new ResponseEntity<>("The origin account does not exist", HttpStatus.FORBIDDEN);
        }
        //Verificar existencia de la cuenta de destino
        if(!accountRepository.findByNumber(accountDestination).equals(accountDestination)){
            return new ResponseEntity<>("The destination account does noy exist", HttpStatus.FORBIDDEN);
        }
        //Verificacion de que las cuentas de Origen y Destino sean distintas
        if (accountDestination.equals(accountOrigin)){
            return new ResponseEntity<>("The Origin account can't be the same as the destintation account", HttpStatus.FORBIDDEN);
        }
        //Verificacion de un monto valido
        if (amount <= 0) {
            return new ResponseEntity<>("Enter a valid amount", HttpStatus.FORBIDDEN);
        }
        //Verificacion de entrada de descripción
        if (description.isEmpty()) {
            return new ResponseEntity<>("Enter a description", HttpStatus.FORBIDDEN);
        }
        //Verificacion de que no este vacio
        if (accountOrigin.isEmpty() || accountDestination.isEmpty()) {
            return new ResponseEntity<>("Account fields can't be empty", HttpStatus.FORBIDDEN);
        }
        //Verificacion de los montos necesarios para transderir
        if (amount > Originaccount.getBalance()) {
            return new ResponseEntity<>("Balance is less than the amount to transfer", HttpStatus.FORBIDDEN);
        }
        //Autenticacion
        if (!authenticated.getAccounts().contains(Originaccount))
            return new ResponseEntity<>("Origin account doesn't belong to the auth client", HttpStatus.FORBIDDEN);

        //Transaccion
        Double debitAmount = amount * -1;
        String OriginTransactionDescription = description + " " + DestAccount;
        String DestinationTransactionDescription = description + " " + Originaccount;

        Transaction sendMoney = new Transaction(TransactionType.DEBIT, debitAmount, LocalDateTime.now(), OriginTransactionDescription);
        Transaction getMoney = new Transaction(TransactionType.CREDIT, amount, LocalDateTime.now(), DestinationTransactionDescription);

        Originaccount.addTransaction(sendMoney);
        DestAccount.addTransaction(getMoney);
        transactionRepository.save(sendMoney);
        transactionRepository.save(getMoney);

        Originaccount.setBalance(Originaccount.getBalance() - amount);
        DestAccount.setBalance(DestAccount.getBalance() + amount);
        accountRepository.save(Originaccount);
        accountRepository.save(DestAccount);

        return new ResponseEntity<>("Transaction completed", HttpStatus.CREATED);

    }

}
