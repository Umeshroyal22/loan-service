package com.example.demo.dto;

import lombok.*;
import java.time.LocalDate;
import com.example.demo.entity.LoanType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDTO {

    private Long applicationNo;
    private Long accountNo;
    private LocalDate applicationDate;
    private Double amount;
    private Integer tenure;
    private Double roi;
    private String status;
    private LoanType loanType;
}