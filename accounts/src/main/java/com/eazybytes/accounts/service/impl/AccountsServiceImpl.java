package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.model.AccountsDTO;
import com.eazybytes.accounts.model.CustomerDTO;
import com.eazybytes.accounts.model.mapper.AccountsMapper;
import com.eazybytes.accounts.model.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * @param customerDTO
     */
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(new Customer(), customerDTO);
        Optional<Customer> optionalCust = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (optionalCust.isPresent()) {
            throw new CustomerAlreadyExistException("Customer already registered with given mobileNumber");
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Admin");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randNum = 100000000L + new Random().nextInt(9000000);

        accounts.setAccountNumber(randNum);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        accounts.setCreatedAt(LocalDateTime.now());
        accounts.setCreatedBy("Admin");
        return accounts;
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber));

        Accounts accountDetails = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "CustomerId", String.valueOf(customer.getCustomerId())));

        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        AccountsDTO accountsDTO = AccountsMapper.mapToAccountsDto(accountDetails, new AccountsDTO());
        customerDTO.setAccountsDto(accountsDTO);
        return customerDTO;
    }

}
