package com.ap.homebanking.services;

import com.ap.homebanking.dto.LoanApplicationDTO;
import com.ap.homebanking.dto.LoanDTO;
import com.ap.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    public List<LoanDTO> getLoans();
    public Loan getLoanById(LoanApplicationDTO loanApplicationDTO);
    public boolean loanExistsById(LoanApplicationDTO loanApplicationDTO);
}
