package com.microservices.accounts.service;

import com.microservices.accounts.model.CustomerDTO;

public interface IAccountsService {

    /**
     * @param customerDTO
     */
    void createAccount(CustomerDTO customerDTO);
    CustomerDTO fetchAccount(String mobileNumber);
    boolean updateAccount(CustomerDTO customerDTO);
    boolean deleteAccount(String mobileNumber);
}
