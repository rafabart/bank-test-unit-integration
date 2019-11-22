package com.invillia.banktestunitintegration.integration;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

import com.invillia.banktestunitintegration.domain.Account;
import com.invillia.banktestunitintegration.domain.Customer;
import com.invillia.banktestunitintegration.domain.request.AccountRequest;
import com.invillia.banktestunitintegration.repository.AccountRepository;
import com.invillia.banktestunitintegration.repository.CustomerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SaveAccountIntegrationTest {

    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public SaveAccountIntegrationTest(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }


    @Test
    void createAccountWithSuccessTest() {
        final AccountRequest accountRequest = AccountRequest.builder()
                .numberAccount("123456")
                .agency("1234")
                .balance(1000.00)
                .limitAccount(0.00)
                .accountTypeString("SAVINGS")
                .idCustomer(1L)
                .build();

        final Customer customer = new Customer();
        customer.setName("Rafael Marinho Cespedes");
        customer.setCpf("307.362.854-74");

        customerRepository.save(customer);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(accountRequest)
                .when()
                .post("/accounts")
                .then()
                .log().all()
                .statusCode(SC_CREATED)
                .header("Location", Matchers.endsWith("/accounts/1"));

        Assertions.assertEquals(1, accountRepository.count());

        final Account account = accountRepository.findById(1L).get();

        Assertions.assertAll("book assert",
                () -> Assertions.assertEquals(accountRequest.getAgency(), account.getAgency()),
                () -> Assertions.assertEquals(accountRequest.getNumberAccount(), account.getNumberAccount()));
    }
}
