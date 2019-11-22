package com.invillia.banktestunitintegration.integration.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import com.github.javafaker.Faker;
import com.invillia.banktestunitintegration.domain.enums.AccountTypeEnum;
import com.invillia.banktestunitintegration.domain.request.AccountRequest;
import com.invillia.banktestunitintegration.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class AccountRequestFactory extends JBacon<AccountRequest> {

    private final Faker faker;

    public AccountRequestFactory(Faker faker) {
        this.faker = faker;
    }

    @Override
    protected AccountRequest getDefault() {
        List<String> letras = Arrays.asList("CHECKING", "SAVINGS");

        return AccountRequest.builder()
                .numberAccount(String.valueOf(faker.number().digits(6)))
                .idCustomer(faker.number().numberBetween(1L, 100L))
                .balance(faker.number().randomDouble(7, 100, 1000000))
                .limitAccount(faker.number().randomDouble(4, 0, 1000))
                .agency(String.valueOf(faker.number().digits(4)))
                .accountTypeString(letras.get(new Random().nextInt(letras.size())))
                .build();
    }

    @Override
    protected AccountRequest getEmpty() {
        return new AccountRequest();
    }

    @Override
    protected void persist(AccountRequest accountRequest) {
        throw new UnsupportedOperationException();
    }
}
