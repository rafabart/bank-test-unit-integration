package com.invillia.banktestunitintegration.integration;


import com.invillia.banktestunitintegration.domain.Account;
import com.invillia.banktestunitintegration.domain.request.WithdrawRequest;
import com.invillia.banktestunitintegration.factory.AccountFactory;
import com.invillia.banktestunitintegration.repository.AccountRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WithdrawAccountIntegrationTest {

    private final AccountFactory accountFactory;

    private final AccountRepository accountRepository;

    @Autowired
    public WithdrawAccountIntegrationTest(AccountFactory accountFactory, AccountRepository accountRepository) {
        this.accountFactory = accountFactory;
        this.accountRepository = accountRepository;
    }


    @Test
    void WithdrawInAccountWithIdCustomerNotFoundTest() {

        accountFactory.create();

        WithdrawRequest withdrawRequest = new WithdrawRequest(500.00, 2L);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(withdrawRequest)
                .when()
                .post("/accounts/withdraw")
                .then()
                .log().all()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", Matchers.is("Conta de ID 2 não encontrada!"));

    }


    @Test
    void WithdrawInAccountWithSucessTest() {

        Account account = accountFactory.create();

        WithdrawRequest withdrawRequest = new WithdrawRequest(500.00, 1L);

        final Double balance = account.getBalance() - withdrawRequest.getWithdraw();

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(withdrawRequest)
                .when()
                .post("/accounts/withdraw")
                .then()
                .log().all()
                .statusCode(SC_OK);

        Account accountWithWithdraw = accountRepository.findById(1L).get();

        Assertions.assertAll("account assert",
                () -> Assertions.assertEquals(balance, accountWithWithdraw.getBalance()));
    }


    @Test
    void WithdrawInAccountWithoutLimitTest() {

        Account account = accountFactory.create();

        WithdrawRequest withdrawRequest = new WithdrawRequest(2000.00, 1L);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(withdrawRequest)
                .when()
                .post("/accounts/withdraw")
                .then()
                .log().all()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", Matchers.is("Limite de R$ 200.0 excedido!"));

        Account accountWithWithdraw = accountRepository.findById(1L).get();

        Assertions.assertAll("account assert",
                () -> Assertions.assertEquals(account.getBalance(), accountWithWithdraw.getBalance()));
    }


    @Test
    void WithdrawInAccountWithoutNegativeNumber() {

        Account account = accountFactory.create();

        WithdrawRequest withdrawRequest = new WithdrawRequest(-200.00, 1L);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(withdrawRequest)
                .when()
                .post("/accounts/withdraw")
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("errors.defaultMessage[0]", Matchers.is("O valor deve ser maior que zero!"));

        Account accountWithWithdraw = accountRepository.findById(1L).get();

        Assertions.assertAll("account assert",
                () -> Assertions.assertEquals(account.getBalance(), accountWithWithdraw.getBalance()));
    }
}
