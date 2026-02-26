package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.LoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/apply")
    public ResponseEntity<LoanResponseDTO> apply(
            @Valid @RequestBody LoanRequestDTO request) {

        LoanResponseDTO response = loanService.applyLoan(request);

        return ResponseEntity.ok(response);
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
}