package com.invillia.banktestunitintegration.integration.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import com.github.javafaker.Faker;
import com.invillia.banktestunitintegration.domain.request.CustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerRequestFactory extends JBacon<CustomerRequest> {

    private final Faker faker;

    public CustomerRequestFactory(Faker faker) {
        this.faker = faker;
    }

    @Override
    protected CustomerRequest getDefault() {
        return CustomerRequest.builder()
                .name(faker.funnyName().name())
                .cpf(faker.numerify("123.123.123-12"))
                .build();
    }

    @Override
    protected CustomerRequest getEmpty() {
        return new CustomerRequest();
    }

    @Override
    protected void persist(CustomerRequest customerRequest) {
        throw new UnsupportedOperationException();
    }
}
