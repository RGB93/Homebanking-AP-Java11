package com.ap.homebanking.services.implement;

import com.ap.homebanking.dto.LoanApplicationDTO;
import com.ap.homebanking.dto.LoanDTO;
import com.ap.homebanking.models.Loan;
import com.ap.homebanking.repositories.LoanRepository;
import com.ap.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoanServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoanDTO> getLoans() {
        return null;
    }

    @Override
    public Loan getLoanById(LoanApplicationDTO loanApplicationDTO) {
        return null;
    }

    @Override
    public boolean loanExistsById(LoanApplicationDTO loanApplicationDTO) {
        return false;
    }
}
