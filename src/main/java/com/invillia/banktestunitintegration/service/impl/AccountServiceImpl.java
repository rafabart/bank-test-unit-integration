package com.invillia.banktestunitintegration.service.impl;

import com.invillia.banktestunitintegration.domain.Account;
import com.invillia.banktestunitintegration.domain.Customer;
import com.invillia.banktestunitintegration.domain.enums.AccountTypeEnum;
import com.invillia.banktestunitintegration.domain.request.AccountRequest;
import com.invillia.banktestunitintegration.domain.request.DepositRequest;
import com.invillia.banktestunitintegration.domain.request.WithdrawRequest;
import com.invillia.banktestunitintegration.domain.response.AccountResponse;
import com.invillia.banktestunitintegration.exception.*;
import com.invillia.banktestunitintegration.mapper.AccountMapper;
import com.invillia.banktestunitintegration.repository.AccountRepository;
import com.invillia.banktestunitintegration.repository.CustomerRepository;
import com.invillia.banktestunitintegration.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    private final AccountMapper accountMapper;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.accountMapper = accountMapper;
    }


    public AccountResponse deposit(DepositRequest depositRequest) {

        if (depositRequest.getDeposit() > 00.00) {

            final Account account = accountRepository.findById(depositRequest.getIdAccount()).orElseThrow(
                    () -> new AccountNotFoundException(depositRequest.getIdAccount())
            );

            account.setBalance(account.getBalance() + depositRequest.getDeposit());

            final Account accountSaved = accountRepository.save(account);

            return accountMapper.accountToAccountResponse(accountSaved);

        } else {
            throw new NotPositiveNumberException();
        }

    }


    public AccountResponse withdraw(final WithdrawRequest withdrawRequest) {

        if (withdrawRequest.getWithdraw() > 00.00) {

            final Account account = accountRepository.findById(withdrawRequest.getIdAccount()).orElseThrow(
                    () -> new AccountNotFoundException(withdrawRequest.getIdAccount())
            );

            if (checkAccountLimit(account, withdrawRequest)) {

                account.setBalance(account.getBalance() - withdrawRequest.getWithdraw());

            } else {
                throw new AccountLimitExceededException(account.getLimitAccount());
            }

            final Account accountSaved = accountRepository.save(account);

            return accountMapper.accountToAccountResponse(accountSaved);

        } else {
            throw new NotPositiveNumberException();
        }
    }


    @Transactional(readOnly = true)
    public List<AccountResponse> find(final AccountRequest accountRequestFilter) {

        Customer customer = new Customer();

        if (accountRequestFilter.getIdCustomer() != null) {

            customer = customerRepository.findById(accountRequestFilter.getIdCustomer()).orElseThrow(
                    () -> new CustomerNotFoundException(accountRequestFilter.getIdCustomer())
            );
        }

        Account accountFilter = accountMapper.accountRequestToAccount(accountRequestFilter, customer);

        final Example exampleAccount = Example.of(accountFilter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        final List<Account> accounts = accountRepository.findAll(exampleAccount);

        return accountMapper.accountToAccountResponse(accounts);
    }


    @Transactional(readOnly = true)
    public AccountResponse findById(final Long id) {

        final Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(id)
        );

        return accountMapper.accountToAccountResponse(account);
    }


    public void update(final Long id, final AccountRequest accountRequest) {

        final Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(id)
        );

        accountMapper.updateAccountByAccountRequest(account, accountRequest);

        accountRepository.save(account);
    }


    public void delete(final Long id) {

        final Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(id)
        );

        accountRepository.delete(account);
    }


    public Long save(final AccountRequest accountRequest) {

        final Customer customer = customerRepository.findById(accountRequest.getIdCustomer()).orElseThrow(
                () -> new CustomerNotFoundException(accountRequest.getIdCustomer())
        );

        final Account account = accountMapper.accountRequestToAccount(accountRequest, customer);

        final Account accountSaved = accountRepository.save(account);

        return accountSaved.getId();
    }


    private boolean checkAccountLimit(final Account account, final WithdrawRequest withdrawRequest) {

        return (-1 * account.getLimitAccount() <= (account.getBalance() - withdrawRequest.getWithdraw()));
    }
}
