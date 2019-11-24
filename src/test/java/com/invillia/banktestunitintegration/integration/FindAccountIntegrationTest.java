package com.invillia.banktestunitintegration.integration;

import com.invillia.banktestunitintegration.domain.Account;
import com.invillia.banktestunitintegration.domain.enums.AccountTypeEnum;
import com.invillia.banktestunitintegration.factory.AccountFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FindAccountIntegrationTest {

    private final AccountFactory accountFactory;

    @Autowired
    public FindAccountIntegrationTest(AccountFactory accountFactory) {
        this.accountFactory = accountFactory;
    }


    @Test
    void findAllWithSuccessTest() {

        accountFactory.create(10);

        RestAssured
                .given()
                .log().all()
                .when()
                .get("/accounts")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("$", Matchers.hasSize(10));
    }


    @Test
    void findAllByNumberAccountWithSuccessTest() {

        accountFactory.create(10);

        Account account = new Account();
        account.setNumberAccount("999999");

        accountFactory.create(account);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("numberAccount", account.getNumberAccount())
                .when()
                .get("/accounts")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("$", Matchers.hasSize(1))
                .body("numberAccount[0]", Matchers.is(account.getNumberAccount()));
    }


    @Test
    void findAllByAgencyWithSuccessTest() {

        accountFactory.create(10);

        Account account = new Account();
        account.setAgency("9999");

        accountFactory.create(account);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("agency", account.getAgency())
                .when()
                .get("/accounts")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("$", Matchers.hasSize(1))
                .body("agency[0]", Matchers.is(account.getAgency()));
    }


    @Test
    void findAllByBalanceWithSuccessTest() {

        accountFactory.create(10);

        Account account = new Account();
        account.setBalance(999999.99);

        accountFactory.create(account);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("balance", account.getBalance())
                .when()
                .get("/accounts")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("$", Matchers.hasSize(1))
                .body("balance[0]", Matchers.is(account.getBalance().floatValue()));
    }

    @Test
    void findAllByLimitAccountWithSuccessTest() {

        accountFactory.create(10);

        Account account = new Account();
        account.setLimitAccount(9999.99);

        accountFactory.create(account);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("limitAccount", account.getLimitAccount())
                .when()
                .get("/accounts")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("$", Matchers.hasSize(1))
                .body("limitAccount[0]", Matchers.is(account.getLimitAccount().floatValue()));
    }

    @Test
    void findAllByAccountTypeEnumtWithSuccessTest() {

        accountFactory.create(10);

        Account account = new Account();
        account.setAccountTypeEnum(AccountTypeEnum.CHECKING);

        accountFactory.create(account);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("accountTypeString", account.getAccountTypeEnum().toString())
                .when()
                .get("/accounts")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("$", Matchers.hasSize(1))
                .body("accountTypeString[0]", Matchers.is(account.getAccountTypeEnum().toString()));
    }

    @Test
    void findAllByCustomerIdWithSuccessTest() {

        accountFactory.create(10);

        int idCustomer = 1;

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("idCustomer", idCustomer)
                .when()
                .get("/accounts")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("$", Matchers.hasSize(1))
                .body("idCustomer[0]", Matchers.is(idCustomer));
    }
}
