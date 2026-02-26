package com.example.demo.dto;

import lombok.Data;

@Data
public class CustomerResponseDTO {

    private Long accountNo;
    private String name;
    private String pan;
    private String email;
}