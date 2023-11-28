package com.eazybytes.accounts.service;

import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.model.CustomerDTO;

public interface IAccountsService {

    /**
     * @param customerDTO
     */
    void createAccount(CustomerDTO customerDTO);
    CustomerDTO fetchAccount(String mobileNumber);
}
