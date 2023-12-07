package com.eazybytes.accounts.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    @NotEmpty(message ="Name cannot be null or empty")
    @Size(min = 3, max =30, message = "Length of the customer name should be between 3 and 30")
    private String name;

    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email provided is not a valid email")
    private String email;

    @NotEmpty(message = "mobileNumber cannot be null or empty")
    @Pattern(regexp = "^04[0-9]{8}$", message = "Number should be a valid Australian Number starting with 04")
    private String mobileNumber;

    private AccountsDTO accountsDto;
}
