package com.ap.homebanking.services;

import com.ap.homebanking.dto.TransactionDTO;
import com.ap.homebanking.models.Transaction;

import java.util.Set;

public interface TransactionService {
    Set<TransactionDTO> getTransactions();
    public Transaction findById(Long id);
    public void saveTransaction(Transaction transaction);
}
