package com.invillia.banktestunitintegration.service;

import com.invillia.banktestunitintegration.domain.request.AccountRequest;
import com.invillia.banktestunitintegration.domain.request.DepositRequest;
import com.invillia.banktestunitintegration.domain.request.WithdrawRequest;
import com.invillia.banktestunitintegration.domain.response.AccountResponse;

import java.util.List;

public interface AccountService {

    public Long withdraw(WithdrawRequest withdrawRequest);

    public Long deposit(DepositRequest depositRequest);

    public List<AccountResponse> find(final AccountRequest accountRequestFilter);

    public AccountResponse findById(final Long id);

    public Long update(final Long id, final AccountRequest accountRequest);

    public void delete(final Long id);

    public Long save(final AccountRequest accountRequest);
}