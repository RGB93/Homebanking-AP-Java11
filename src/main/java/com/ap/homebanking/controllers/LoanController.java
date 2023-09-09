package com.ap.homebanking.controllers;

import com.ap.homebanking.dto.LoanApplicationDTO;
import com.ap.homebanking.dto.LoanDTO;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;


    @RequestMapping(path = "/loans")
    public ResponseEntity<Object> getLoans() {
        List<LoanDTO> loanDTOS = loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(loanDTOS, HttpStatus.OK);
    }


    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> addLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Account accountDestination = accountRepository.findByNumber(loanApplication.getToAccountNumber());

        System.out.println(loanApplication.getLoanId());

        if (loanApplication.getLoanId() == null || loanApplication.getLoanId() <= 0) {
            return new ResponseEntity<>("The ID can not be null and must not be 0", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanRepository.findById(loanApplication.getLoanId()).orElse(null);


        // Prestamo nulo
        if (loanApplication == null){
            return new ResponseEntity<>("The loan application is null.", HttpStatus.FORBIDDEN);
        }

        // Monto mayor a 0
        if (loanApplication.getAmount() <= 0) {
            return new ResponseEntity<>("The amount of the loan must be higher than 0.", HttpStatus.FORBIDDEN);
        }

        // Pagos mayores a 0
        if (loanApplication.getPayments() <= 0) {
            return new ResponseEntity<>("The payments of the loan must be higher than 0.", HttpStatus.FORBIDDEN);
        }

        if (loanApplication.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("The max amount of the loan is " + loan.getMaxAmount(), HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplication.getPayments())) {
            return new ResponseEntity<>("The amount of payments is incorrect", HttpStatus.FORBIDDEN);
        }

        // Verificar existencia de la cuenta de destino
        if (!loanApplication.getToAccountNumber().equals(accountDestination.getNumber())) {
            return new ResponseEntity<>("The account of destination does not exist.", HttpStatus.FORBIDDEN);
        }

        // Verificar cuenta del cliente
        if (!accountDestination.getClient().equals(client)) {
            return new ResponseEntity<>("The account of destination must be the same as the client who is logged in.", HttpStatus.FORBIDDEN);
        }


        // Solicitud de préstamo con el monto solicitado sumando un % de interes
        // Hipoteca = 20%    ;   Personal = 25%  ;   Automotriz = 30%
        if (loan.getName().equalsIgnoreCase("Mortgage")) {
            Double amount20 = (loanApplication.getAmount() * 1.20);
            ClientLoan clientLoan = new ClientLoan(amount20, loanApplication.getPayments());

            client.addClientLoan(clientLoan);
            loan.addClientsLoans(clientLoan);

            clientLoanRepository.save(clientLoan);
        }
        if (loan.getName().equalsIgnoreCase("Personal")) {
            Double amount30 = (loanApplication.getAmount() * 1.25);
            ClientLoan clientLoan = new ClientLoan(amount30, loanApplication.getPayments());

            client.addClientLoan(clientLoan);
            loan.addClientsLoans(clientLoan);

            clientLoanRepository.save(clientLoan);
        }
        if (loan.getName().equalsIgnoreCase("Automotive")) {
            Double amount40 = (loanApplication.getAmount() * 1.30);
            ClientLoan clientLoan = new ClientLoan(amount40, loanApplication.getPayments());

            client.addClientLoan(clientLoan);
            loan.addClientsLoans(clientLoan);

            clientLoanRepository.save(clientLoan);
        }

        // Crear las transacciones y guardarlas en el repo
        LocalDateTime date = LocalDateTime.now();
        Transaction transactionLoan = new Transaction(TransactionType.CREDIT, loanApplication.getAmount(), date, loan.getName() + " Loan approved");
        accountDestination.addTransaction(transactionLoan);

        transactionRepository.save(transactionLoan);
        accountDestination.setBalance(accountDestination.getBalance() + loanApplication.getAmount());
        accountRepository.save(accountDestination);

        return new ResponseEntity<>("Credit Loan", HttpStatus.CREATED);
    }
}
