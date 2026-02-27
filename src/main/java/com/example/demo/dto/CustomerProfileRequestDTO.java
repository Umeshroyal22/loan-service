package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerProfileRequestDTO {

    @NotBlank(message = "PAN is required")
    @Pattern(
        regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$",
        message = "Invalid PAN format"
    )
    private String pan;


    @NotNull(message = "Date of Birth is required")
    private LocalDate dob;


    @NotBlank(message = "Address is required")
    private String address;


    @NotBlank(message = "Bank Account Number is required")
    @Pattern(
        regexp = "^[0-9]{9,18}$",
        message = "Invalid Bank Account Number"
    )
    private String bankAccount;


    @NotBlank(message = "IFSC Code is required")
    @Pattern(
        regexp = "^[A-Z]{4}0[A-Z0-9]{6}$",
        message = "Invalid IFSC Code"
    )
    private String ifsc;


    @Min(value = 10000, message = "Annual Income must be above 10000")
    private double annualIncome;


    @NotBlank(message = "Occupation is required")
    private String occupation;

}