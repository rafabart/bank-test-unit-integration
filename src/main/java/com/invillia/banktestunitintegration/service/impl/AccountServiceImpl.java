package com.invillia.banktestunitintegration.service.impl;

import com.invillia.banktestunitintegration.domain.Account;
import com.invillia.banktestunitintegration.domain.enums.AccountTipyEnum;
import com.invillia.banktestunitintegration.domain.request.AccountRequest;
import com.invillia.banktestunitintegration.domain.request.DepositRequest;
import com.invillia.banktestunitintegration.domain.request.WithdrawRequest;
import com.invillia.banktestunitintegration.domain.response.AccountResponse;
import com.invillia.banktestunitintegration.exception.AccountLimitExceededException;
import com.invillia.banktestunitintegration.exception.AccountNotFoundException;
import com.invillia.banktestunitintegration.mapper.AccountMapper;
import com.invillia.banktestunitintegration.repository.AccountRepository;
import com.invillia.banktestunitintegration.repository.PersonRepository;
import com.invillia.banktestunitintegration.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.WildcardType;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final PersonRepository personRepository;

    private final AccountMapper accountMapper;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PersonRepository personRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
        this.accountMapper = accountMapper;
    }


    public Long deposit(DepositRequest depositRequest) {
        final Account account = accountRepository.findById(depositRequest.getIdAccount()).orElseThrow(() -> new AccountNotFoundException(
                "Conta de ID " + depositRequest.getIdAccount() + " não encontrada!"));

        account.setBalance(account.getBalance() + depositRequest.getDeposit());

        final Account accountSaved = accountRepository.save(account);

        return accountSaved.getId();
    }


    public Long withdraw(WithdrawRequest withdrawRequest) {
        final Account account = accountRepository.findById(withdrawRequest.getIdAccount()).orElseThrow(() -> new AccountNotFoundException(
                "Conta de ID " + withdrawRequest.getIdAccount() + " não encontrada!"));

        if (!((account.getBalance() - withdrawRequest.getWithdraw()) < -1 * account.getLimitAccount())) {
            account.setBalance(account.getBalance() - withdrawRequest.getWithdraw());
        } else {
            throw new AccountLimitExceededException(
                    "Limite de R$ " + account.getLimitAccount() + " excedido!");
        }
        final Account accountSaved = accountRepository.save(account);

        return accountSaved.getId();
    }


    @Transactional(readOnly = true)
    public List<AccountResponse> find(final AccountRequest accountRequestFilter) {

        Account accountFilter = accountMapper.accountRequestToAccount(accountRequestFilter);

        final Example exampleAccount = Example.of(accountFilter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        final List<Account> accounts = accountRepository.findAll(exampleAccount);

        return accountMapper.accountToAccountResponse(accounts);
    }


    @Transactional(readOnly = true)
    public AccountResponse findById(final Long id) {
        final Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(
                "Conta de ID " + id + " não encontrada!"));

        return accountMapper.accountToAccountResponse(account);
    }


    public Long update(final Long id, final AccountRequest accountRequest) {

        final Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(
                "Conta de ID " + id + " não encontrada!"));

        accountRequest.setId(account.getId());
        accountMapper.updateAccountByAccountRequest(account, accountRequest);

        if (accountRequest.getAccountTipyString().equals("SAVINGS") ||
                accountRequest.getAccountTipyString().equals("CHECKING")) {
            account.setAccountTipyEnum(AccountTipyEnum.valueOf(accountRequest.getAccountTipyString()));
        } else {
            throw new AccountNotFoundException("Tipo de conta não cadastrada");
        }

        account.setPerson(personRepository.findById(accountRequest.getIdPerson()).orElseThrow(
                () -> new AccountNotFoundException("Pessoa de ID não encontrada!")));

        final Account accountSaved = accountRepository.save(account);

        return accountSaved.getId();
    }


    public void delete(final Long id) {
        final Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(
                "Pessoa de ID " + id + " não encontrada!"));

        accountRepository.delete(account);
    }


    public Long save(final AccountRequest accountRequest) {

        final Account account = accountMapper.accountRequestToAccount(accountRequest);

        if (accountRequest.getAccountTipyString().equals("SAVINGS") ||
                accountRequest.getAccountTipyString().equals("CHECKING")) {
            account.setAccountTipyEnum(AccountTipyEnum.valueOf(accountRequest.getAccountTipyString()));
        } else {
            throw new AccountNotFoundException("Tipo de conta não cadastrada");
        }

        account.setPerson(personRepository.findById(accountRequest.getIdPerson()).orElseThrow(
                () -> new AccountNotFoundException("Pessoa de ID não encontrada!")));

        final Account accountSaved = accountRepository.save(account);

        return accountSaved.getId();
    }
}
