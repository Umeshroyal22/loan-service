package com.example.demo.service;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.dto.LoanResponseDTO;
import com.example.demo.entity.LoanApplication;
import com.example.demo.repository.LoanRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanRepository repo;

    @InjectMocks
    private LoanServiceImpl service;

    // 🔹 APPLY LOAN TEST
    @Test
    void testApplyLoan() {

        LoanRequestDTO request = new LoanRequestDTO();
        request.setAccountNo(1001L);
        request.setAmount(50000.0);
        request.setTenure(12);
        request.setRoi(8.5);

        when(repo.save(any(LoanApplication.class)))
                .thenAnswer(invocation -> {
                    LoanApplication loan = invocation.getArgument(0);
                    loan.setApplicationNo(1L);
                    loan.setApplicationDate(LocalDate.now());
                    loan.setStatus("PENDING");
                    return loan;
                });

        LoanResponseDTO response = service.applyLoan(request);

        assertNotNull(response);
        assertEquals("PENDING", response.getStatus());
        assertEquals(1001L, response.getAccountNo());

        verify(repo, times(1)).save(any(LoanApplication.class));
    }

    // 🔹 CANCEL LOAN TEST
    @Test
    void testCancelLoan() {

        doNothing().when(repo).deleteById(1L);

        service.cancelLoan(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    // 🔹 GET ALL LOANS TEST
    @Test
    void testGetAllLoans() {

        LoanApplication loan = new LoanApplication();
        loan.setApplicationNo(1L);
        loan.setAccountNo(1001L);
        loan.setStatus("PENDING");

        when(repo.findAll()).thenReturn(List.of(loan));

        var result = service.getAllLoans();

        assertEquals(1, result.size());
    }

    // 🔹 UPDATE LOAN TEST
    @Test
    void testUpdateLoan() {

        LoanApplication loan = new LoanApplication();
        loan.setApplicationNo(1L);
        loan.setAccountNo(1001L);
        loan.setStatus("PENDING");

        when(repo.findById(1L)).thenReturn(Optional.of(loan));
        when(repo.save(any())).thenReturn(loan);

        LoanRequestDTO req = new LoanRequestDTO();
        req.setAmount(70000.0);
        req.setTenure(24);
        req.setRoi(9.0);

        LoanResponseDTO response = service.updateLoan(1L, req);

        assertNotNull(response);
    }
}