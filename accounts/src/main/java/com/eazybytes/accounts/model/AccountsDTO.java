package com.eazybytes.accounts.model;

import jakarta.persistence.Column;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsDTO {
    private long accountNumber;

    private String accountType;

    private String branchAddress;
}
