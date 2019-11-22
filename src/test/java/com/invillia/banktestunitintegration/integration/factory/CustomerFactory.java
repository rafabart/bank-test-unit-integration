package com.invillia.banktestunitintegration.integration.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import com.github.javafaker.Faker;
import com.invillia.banktestunitintegration.domain.Customer;
import com.invillia.banktestunitintegration.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerFactory extends JBacon<Customer> {

    private final CustomerRepository customerRepository;

    private final Faker faker;

    public CustomerFactory(CustomerRepository customerRepository, Faker faker) {
        this.customerRepository = customerRepository;
        this.faker = faker;
    }

    @Override
    protected Customer getDefault() {
        final Customer customer = new Customer();

        customer.setName(faker.funnyName().name());
        customer.setCpf(faker.numerify("123.123.123-12"));
        return customer;
    }

    @Override
    protected Customer getEmpty() {
        return new Customer();
    }

    @Override
    protected void persist(Customer customer) {
        customerRepository.save(customer);
    }
}
