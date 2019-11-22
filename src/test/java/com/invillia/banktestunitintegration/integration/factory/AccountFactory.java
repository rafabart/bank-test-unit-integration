package com.invillia.banktestunitintegration.integration.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import com.github.javafaker.Faker;
import com.invillia.banktestunitintegration.domain.Account;
import com.invillia.banktestunitintegration.domain.Customer;
import com.invillia.banktestunitintegration.domain.enums.AccountTypeEnum;
import com.invillia.banktestunitintegration.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class AccountFactory extends JBacon<Account> {

    private final AccountRepository accountRepository;

    private final Faker faker;

    private final CustomerFactory customerFactory;

    public AccountFactory(AccountRepository accountRepository, Faker faker, CustomerFactory customerFactory) {
        this.accountRepository = accountRepository;
        this.faker = faker;
        this.customerFactory = customerFactory;
    }

    @Override
    protected Account getDefault() {

        List<String> letras = Arrays.asList("CHECKING", "SAVINGS");

        Customer customer = customerFactory.create();

        final Account account = new Account();

        account.setNumberAccount(String.valueOf(faker.number().digits(6)));
        account.setCustomer(customer);
        account.setBalance(faker.number().randomDouble(7, 100, 1000000));
        account.setLimitAccount(faker.number().randomDouble(4, 0, 1000));
        account.setAgency(String.valueOf(faker.number().digits(4)));
        account.setAccountTypeEnum(AccountTypeEnum.valueOf(letras.get(new Random().nextInt(letras.size()))));
        return account;
    }

    @Override
    protected Account getEmpty() {
        return new Account();
    }

    @Override
    protected void persist(final Account account) {
        accountRepository.save(account);
    }
}
