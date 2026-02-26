package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.LoanApplication;
import com.example.demo.repository.LoanRepository;
import com.example.demo.exception.LoanNotFoundException;
import com.example.demo.feign.CustomerFeignClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final CustomerFeignClient customerFeignClient;

    
    
    
    
    @Override
    public LoanResponseDTO applyLoan(LoanRequestDTO request) {

        // 🔥 CALL CUSTOMER SERVICE
        CustomerLoanInfoDTO customer =
                customerFeignClient.getCustomerLoanInfo(request.getAccountNo());

        if (customer == null) {
            throw new RuntimeException("Customer not found in customer-service");
        }

        LoanApplication loan = new LoanApplication();
        loan.setAccountNo(customer.getAccountNo());
        loan.setAmount(request.getAmount());
        loan.setTenure(request.getTenure());
        loan.setRoi(request.getLoanType().getRoi());
        loan.setLoanType(request.getLoanType());   // 🔥 IMPORTANT
        loan.setApplicationDate(LocalDate.now());
        loan.setPan(customer.getPan());
        loan.setStatus("PENDING");

        LoanApplication savedLoan = loanRepository.save(loan);

        return mapToResponse(savedLoan);
    }

    @Override
    public LoanResponseDTO updateLoan(Long id, LoanRequestDTO request) {

        LoanApplication existing = loanRepository.findById(id)
                .orElseThrow(() ->
                        new LoanNotFoundException("Loan Not Found"));

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
                .orElseThrow(() ->
                        new LoanNotFoundException("Loan Not Found"));

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
                .orElseThrow(() ->
                        new RuntimeException("Loan Not Found"));

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
                loan.getLoanType()
        );
    }
}