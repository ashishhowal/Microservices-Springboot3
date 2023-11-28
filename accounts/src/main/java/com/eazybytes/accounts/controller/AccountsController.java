/**
 *
 */
package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.model.CustomerDTO;
import com.eazybytes.accounts.model.Response.ResponseDTO;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;


@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountsController {

    private IAccountsService iAccountsService;


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO){
        iAccountsService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }

    @GetMapping(value = "/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam String mobileNumber){
        CustomerDTO customerDTO = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

}
