package com.eazybytes.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.accounts.entity.Accounts;

import java.util.Optional;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

	Optional<Accounts> findByCustomerId(int customerId);


}
