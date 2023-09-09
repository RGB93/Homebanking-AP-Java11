package com.ap.homebanking.services.implement;

import com.ap.homebanking.dto.TransactionDTO;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.repositories.TransactionRepository;
import com.ap.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public Set<TransactionDTO> getTransactions() {
        return null;
    }

    @Override
    public Transaction findById(Long id) {
        return null;
    }

    @Override
    public void saveTransaction(Transaction transaction) {

    }
}
