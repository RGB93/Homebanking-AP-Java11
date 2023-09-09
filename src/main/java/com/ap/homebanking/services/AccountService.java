package com.ap.homebanking.services;

import com.ap.homebanking.dto.AccountDTO;
import com.ap.homebanking.models.Account;

import java.util.Set;

public interface AccountService {
    Set<AccountDTO> getAccountsDto();
    AccountDTO getAccountDtoById(Long id);
    boolean existsByNumber(String number);
    void saveAccount(Account account);
    Account getAccountByNumber(String number);

}
