package com.invillia.banktestunitintegration.mapper;

import com.invillia.banktestunitintegration.domain.Account;
import com.invillia.banktestunitintegration.domain.request.AccountRequest;
import com.invillia.banktestunitintegration.domain.response.AccountResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public AccountResponse accountToAccountResponse(final Account account) {

        return AccountResponse.builder()
                .id(account.getId())
                .numberAccount(account.getNumberAccount())
                .agency(account.getAgency())
                .balance(account.getBalance())
                .limitAccount(account.getLimitAccount())
                .accountTipyString(account.getAccountTipyEnum().toString())
                .idPerson(account.getPerson().getId())
                .createdAt(account.getCreatedAt().format(formatter))
                .updatedAt(account.getUpdatedAt().format(formatter))
                .build();
    }

    public List<AccountResponse> accountToAccountResponse(final List<Account> accounts) {

        return accounts.stream()
                .map(this::accountToAccountResponse)
                .collect(Collectors.toList());
    }

    public Account accountRequestToAccount(final AccountRequest accountRequest) {

        Account account = new Account();
        account.setId(accountRequest.getId());
        account.setNumberAccount(accountRequest.getNumberAccount());
        account.setAgency(accountRequest.getAgency());
        account.setBalance(accountRequest.getBalance());
        account.setLimitAccount(accountRequest.getLimitAccount());
        return account;
    }

    public void updateAccountByAccountRequest(final Account account, final AccountRequest accountRequest) {

        account.setId(accountRequest.getId());
        account.setNumberAccount(accountRequest.getNumberAccount());
        account.setAgency(accountRequest.getAgency());
        account.setBalance(accountRequest.getBalance());
        account.setLimitAccount(accountRequest.getLimitAccount());
    }
}
