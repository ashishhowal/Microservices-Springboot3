package com.microservices.accounts.service.impl;

import com.microservices.accounts.constants.AccountsConstants;
import com.microservices.accounts.entity.Accounts;
import com.microservices.accounts.entity.Customer;
import com.microservices.accounts.exception.CustomerAlreadyExistException;
import com.microservices.accounts.exception.ResourceNotFoundException;
import com.microservices.accounts.model.AccountsDTO;
import com.microservices.accounts.model.CustomerDTO;
import com.microservices.accounts.model.mapper.AccountsMapper;
import com.microservices.accounts.model.mapper.CustomerMapper;
import com.microservices.accounts.repository.AccountsRepository;
import com.microservices.accounts.repository.CustomerRepository;
import com.microservices.accounts.service.IAccountsService;
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

    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountsDTO accountsDTO = customerDTO.getAccountsDto();
        if (accountsDTO != null) {
            Accounts accounts = accountsRepository.
                    findById(accountsDTO.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", String.valueOf(accountsDTO.getAccountNumber())));
            AccountsMapper.mapToAccounts(accounts, accountsDTO);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.
                    findById(customerId)
                    .orElseThrow( ()-> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));

            CustomerMapper.mapToCustomer(customer, customerDTO);
            customer.setUpdatedAt(LocalDateTime.now());
            customerRepository.save(customer);
            isUpdated = true;
        }else{
            throw new ResourceNotFoundException("Accounts", "Accounts", "Accounts not found");
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
