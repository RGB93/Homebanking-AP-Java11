package com.ap.homebanking;

import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> {
			Client client2 = new Client();
			client2.setFirstName("Pity");
			client2.setLastName("Martinez");
			client2.setEmail("Pm@gamil.com");
			client2.setPassword(passwordEncoder.encode("1234"));

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com",passwordEncoder.encode("1234"));


			clientRepository.save(client1);
			clientRepository.save(client2);

			Account account1 = new Account();
			account1.setNumber("VIN001");
			account1.setCreationDate(LocalDate.now());
			account1.setBalance(5000.0);

			Account account2 = new Account();
			account2.setNumber("VIN002");
			account2.setCreationDate(LocalDate.now().plus(Period.ofDays(1)));
			account2.setBalance(7500.0);

			client1.addAccount(account1);
			client2.addAccount(account2);

			accountRepository.save(account1);
			accountRepository.save(account2);
			clientRepository.save(client1);
			clientRepository.save(client2);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT, +940.0, LocalDateTime.now(), "Salary");
			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -400.0, LocalDateTime.now(),"Food");
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -315.0,LocalDateTime.now(),"creditCard");
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, +655.0, LocalDateTime.now(),"Many");

			account1.addTransaction(transaction1);
			account2.addTransaction(transaction4);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			accountRepository.save(account1);
			accountRepository.save(account2);

			Loan loan1 = new Loan("Hipotecario",500000.0, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal", 100000.0, List.of(6,12,24));
			Loan loan3 = new Loan("Automotriz", 300000.0, List.of(6,12,24,36));
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000.0, 60, client1, loan1);
			client1.addClientsLoans(clientLoan1);
			loan1.addClientsLoans(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan(50000.0, 12, client1, loan2);
			loan2.addClientsLoans(clientLoan2);
			client1.addClientsLoans(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan(100000.0, 24, client2, loan2);
			client2.addClientsLoans(clientLoan3);
			loan2.addClientsLoans(clientLoan3);

			ClientLoan clientLoan4 = new ClientLoan(200000.0, 36, client2, loan3);
			loan3.addClientsLoans(clientLoan4);
			client2.addClientsLoans(clientLoan4);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card();
			card1.setCardHolder(client1.getFirstName()+" "+client1.getLastName());
			card1.setColor(CardColor.GOLD);
			card1.setType(CardType.DEBIT);
			card1.setFromDate(LocalDate.now());
			card1.setThruDate(LocalDate.now().plusYears(5));
			card1.setNumber("7784 6654 8897 2214");
			card1.setCvv((short) 678);
			client1.addCard(card1);

			Card card2 = new Card();
			card2.setCardHolder(client1.getFirstName()+" "+client1.getLastName());
			card2.setColor(CardColor.TITANIUM);
			card2.setType(CardType.CREDIT);
			card2.setFromDate(LocalDate.now());
			card2.setThruDate(LocalDate.now().plusYears(5));
			card2.setNumber("6784 8774 8897 2214");
			card2.setCvv((short) 945);
			client1.addCard(card2);

			Card card3 = new Card();
			card3.setCardHolder(client2.getFirstName()+" "+client2.getLastName());
			card3.setColor(CardColor.SILVER);
			card3.setType(CardType.CREDIT);
			card3.setFromDate(LocalDate.now());
			card3.setThruDate(LocalDate.now().plusYears(5));
			card3.setNumber("3122 5347 0912 1218");
			card3.setCvv((short) 912);
			client2.addCard(card3);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
		};
    }

}
