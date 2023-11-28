package com.eazybytes.accounts.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String name;
    private String email;
    private String mobileNumber;

    private AccountsDTO accountsDto;
}
