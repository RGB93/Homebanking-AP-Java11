package com.ap.homebanking;

import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Period;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository){
		return args -> {
			Client client2 = new Client();
			client2.setFirstName("Pity");
			client2.setLastName("Martinez");
			client2.setEmail("Pm@gamil.com");

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");


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

		};
	}

}
