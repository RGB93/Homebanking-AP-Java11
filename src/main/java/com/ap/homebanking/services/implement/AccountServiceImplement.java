package com.ap.homebanking.services.implement;

import com.ap.homebanking.dto.AccountDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Set<AccountDTO> getAccountsDto(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toSet());
    }

    @Override
    public AccountDTO getAccountDtoById(Long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    @Override
    public boolean existsByNumber(String number){
        return accountRepository.existsByNumber(number);
    };

    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public Account getAccountByNumber(String number){
        return accountRepository.findByNumber(number);
    }

}
