package com.microservices.accounts.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDTO {
    @Pattern(regexp = "($[0-9]{10})", message = "Account number must be 10 digit")
    @NotEmpty
    @Schema(
            description = "Account Number of Eazy Bank account", example = "3454433243"
    )
    private long accountNumber;

    @Schema(
            description = "Account type of Eazy Bank account", example = "Savings"
    )
    @NotEmpty(message = "account type cannot be null or empty")
    private String accountType;

    @Schema(
            description = "Eazy Bank branch address", example = "123 NewYork"
    )
    @NotEmpty(message = "branch address cannot be null or empty")
    private String branchAddress;
}
