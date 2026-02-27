package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.LoanApplication;
import com.example.demo.repository.LoanRepository;
import com.example.demo.exception.LoanNotFoundException;
import com.example.demo.feign.CustomerClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final CustomerClient customerClient;

    @Override
    public LoanResponseDTO applyLoan(
            LoanRequestDTO dto,
            String username) {

        CustomerResponseDTO customer = customerClient.getCustomerByUsername(username);

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        LoanApplication loan = new LoanApplication();

        loan.setAccountNo(customer.getAccountNo());
        loan.setPan(customer.getPan());

        loan.setAmount(dto.getAmount());
        loan.setTenure(dto.getTenure());

        loan.setLoanType(dto.getLoanType());

        // 🔥 AUTO SET ROI FROM ENUM
        loan.setRoi(dto.getLoanType().getRoi());

        loan.setStatus("PENDING");
        loan.setApplicationDate(LocalDate.now());

        loanRepository.save(loan);

        return mapToResponse(loan);
    }

    @Override
    public LoanResponseDTO updateLoan(Long id, LoanRequestDTO request) {

        LoanApplication existing = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan Not Found"));

        existing.setAmount(request.getAmount());
        existing.setTenure(request.getTenure());
        existing.setLoanType(request.getLoanType());

        return mapToResponse(loanRepository.save(existing));
    }

    @Override
    public void cancelLoan(Long id) {
        loanRepository.deleteById(id);
    }

    @Override
    public LoanResponseDTO getLoanStatus(Long id) {

        LoanApplication loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan Not Found"));

        return mapToResponse(loan);
    }

    @Override
    public List<LoanResponseDTO> getAllLoans() {

        return loanRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LoanResponseDTO getLoanById(Long loanId) {

        LoanApplication loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan Not Found"));

        return mapToResponse(loan);
    }

    private LoanResponseDTO mapToResponse(LoanApplication loan) {

        return new LoanResponseDTO(
                loan.getApplicationNo(),
                loan.getAccountNo(),
                loan.getApplicationDate(),
                loan.getAmount(),
                loan.getTenure(),
                loan.getRoi(),
                loan.getStatus(),
                loan.getLoanType());
    }

    @Override
    public LoanResponseDTO updateLoanStatus(Long id, String status, String reason) {
        LoanApplication existing = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan Not Found"));

        // If loan application had a reason field we would set it here.
        // For now, we only update the status.
        existing.setStatus(status.toUpperCase());
        return mapToResponse(loanRepository.save(existing));
    }
}