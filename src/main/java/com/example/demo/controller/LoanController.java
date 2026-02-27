package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.LoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/apply")
    public ResponseEntity<?> applyLoan(
            @RequestBody LoanRequestDTO dto,
            Authentication authentication) {

        // 🔥 Extract username from JWT
        String username = authentication.getName();

        return ResponseEntity.ok(
                loanService.applyLoan(dto, username));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LoanResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody LoanRequestDTO request) {

        return ResponseEntity.ok(loanService.updateLoan(id, request));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        loanService.cancelLoan(id);
        return ResponseEntity.ok("Loan Cancelled Successfully");
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<LoanResponseDTO> status(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanStatus(id));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponseDTO> getLoanById(
            @PathVariable Long loanId) {

        return ResponseEntity.ok(
                loanService.getLoanById(loanId));
    }

    @PutMapping("/status")
    public ResponseEntity<LoanResponseDTO> updateLoanStatus(
            @RequestParam("applicationId") Long applicationId,
            @RequestParam("status") String status,
            @RequestParam(value = "reason", required = false) String reason) {
        return ResponseEntity.ok(loanService.updateLoanStatus(applicationId, status, reason));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }
}