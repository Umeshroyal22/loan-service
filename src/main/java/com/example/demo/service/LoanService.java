package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.dto.LoanResponseDTO;

public interface LoanService {

    LoanResponseDTO applyLoan(LoanRequestDTO dto, String username);

    LoanResponseDTO updateLoan(Long id, LoanRequestDTO request);

    void cancelLoan(Long id);

    LoanResponseDTO getLoanStatus(Long id);

    List<LoanResponseDTO> getAllLoans();

    LoanResponseDTO getLoanById(Long loanId);

    LoanResponseDTO updateLoanStatus(Long id, String status, String reason);
}