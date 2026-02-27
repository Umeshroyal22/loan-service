package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.CustomerResponseDTO;

@FeignClient(name = "customer-service", url = "http://localhost:8081")
public interface CustomerClient {

    @GetMapping("/customer/username/{username}")
    CustomerResponseDTO getCustomerByUsername(
            @PathVariable("username") String username);
}
