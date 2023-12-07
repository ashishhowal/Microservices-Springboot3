package com.eazybytes.accounts.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsDTO {
    @Pattern(regexp = "($[0-9]{10})", message = "Account number must be 10 digit")
    @NotEmpty
    private long accountNumber;

    @NotEmpty(message = "account type cannot be null or empty")
    private String accountType;
    @NotEmpty(message = "branch address cannot be null or empty")
    private String branchAddress;
}
