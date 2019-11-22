package com.invillia.banktestunitintegration.service;

import com.invillia.banktestunitintegration.domain.Account;
import com.invillia.banktestunitintegration.domain.Customer;
import com.invillia.banktestunitintegration.domain.enums.AccountTypeEnum;
import com.invillia.banktestunitintegration.domain.request.AccountRequest;
import com.invillia.banktestunitintegration.domain.request.DepositRequest;
import com.invillia.banktestunitintegration.domain.response.AccountResponse;
import com.invillia.banktestunitintegration.mapper.AccountMapper;
import com.invillia.banktestunitintegration.repository.AccountRepository;
import com.invillia.banktestunitintegration.repository.CustomerRepository;
import com.invillia.banktestunitintegration.service.impl.AccountServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class AccountServiceTest {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private AccountMapper accountMapper;


    @Test(expected = Test.None.class)
    public void shouldDepositInAccountWithSuccess() {

        Account accountBefore = createAccount();
        Account accountReturn = createAccount();
        accountReturn.setBalance(2000.00);

        DepositRequest depositRequest = DepositRequest.builder()
                .idAccount(1L)
                .deposit(1000.00)
                .build();

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(accountBefore));
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(accountReturn);

        AccountResponse accountAfterDeposit = accountServiceImpl.deposit(depositRequest);

        Assertions.assertThat(accountAfterDeposit.getBalance()).isEqualTo(2000.00);
    }


    @Test(expected = Test.None.class)
    public void shouldSaveAccountWithSuccess() {

        AccountRequest accountRequest = createAccountRequest();
        Customer customer = createCustomer();
        Account account = createAccount();

        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
        Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));
        Mockito.when(accountMapper.accountRequestToAccount(Mockito.any())).thenReturn(account);

        Long idAccount = accountServiceImpl.save(accountRequest);

        Assertions.assertThat(idAccount).isEqualTo(1L);
    }

    public AccountRequest createAccountRequest() {
        return AccountRequest.builder()
                .numberAccount("123456")
                .agency("3214")
                .accountTypeString("CHECKING")
                .limitAccount(0.00)
                .balance(1000.00)
                .idCustomer(1L)
                .build();
    }

    public Customer createCustomer() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Rafael Marinho");
        customer.setCpf("307.033.456-74");

        return customer;
    }

    public Account createAccount() {

        Account account = Account.builder()
                .numberAccount("123456")
                .agency("3214")
                .limitAccount(0.00)
                .balance(1000.00)
                .customer(createCustomer())
                .accountTypeEnum(AccountTypeEnum.CHECKING)
                .build();
        account.setId(1L);

        return account;
    }
}
