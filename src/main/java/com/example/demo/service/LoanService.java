package com.example.demo.service;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.dto.LoanResponseDTO;

import java.util.List;

public interface LoanService {

    LoanResponseDTO applyLoan(LoanRequestDTO request);

    LoanResponseDTO updateLoan(Long id, LoanRequestDTO request);

    void cancelLoan(Long id);

    LoanResponseDTO getLoanStatus(Long id);

    List<LoanResponseDTO> getAllLoans();
    LoanResponseDTO getLoanById(Long loanId);
}