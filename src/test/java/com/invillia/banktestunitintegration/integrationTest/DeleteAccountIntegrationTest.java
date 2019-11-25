package com.invillia.banktestunitintegration.integrationTest;

import com.invillia.banktestunitintegration.factory.AccountFactory;
import com.invillia.banktestunitintegration.repository.AccountRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteAccountIntegrationTest {

    private final AccountFactory accountFactory;

    private final AccountRepository accountRepository;

    @Autowired
    public DeleteAccountIntegrationTest(AccountFactory accountFactory, AccountRepository accountRepository) {
        this.accountFactory = accountFactory;
        this.accountRepository = accountRepository;
    }


    @Test
    public void deleteAccountWithSuccessTest() {

        accountFactory.create();

        RestAssured
                .given()
                .log().all()
                .when()
                .delete("/accounts/1")
                .then()
                .log().all()
                .statusCode(SC_NO_CONTENT);

        Assertions.assertEquals(0, accountRepository.count());
    }

    @Test
    public void deleteByIdNotFoundTest() {

        RestAssured
                .given()
                .log().all()
                .when()
                .delete("/accounts/1")
                .then()
                .log().all()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
