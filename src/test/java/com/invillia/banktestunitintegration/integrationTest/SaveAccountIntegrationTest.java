package com.invillia.banktestunitintegration.integrationTest;

import com.invillia.banktestunitintegration.domain.Account;
import com.invillia.banktestunitintegration.domain.request.AccountRequest;
import com.invillia.banktestunitintegration.factory.AccountRequestFactory;
import com.invillia.banktestunitintegration.repository.AccountRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SaveAccountIntegrationTest {

    private final AccountRepository accountRepository;

    private final AccountRequestFactory accountRequestFactory;

    @Autowired
    public SaveAccountIntegrationTest(AccountRepository accountRepository, AccountRequestFactory accountRequestFactory) {
        this.accountRepository = accountRepository;
        this.accountRequestFactory = accountRequestFactory;
    }

    @Test
    public void shouldcreateAccountWithSuccessTest() {

        final AccountRequest accountRequest = accountRequestFactory.build();

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

        Assertions.assertAll("account assert",
                () -> Assertions.assertEquals(accountRequest.getAgency(), account.getAgency()),
                () -> Assertions.assertEquals(accountRequest.getNumberAccount(), account.getNumberAccount()),
                () -> Assertions.assertEquals(accountRequest.getIdCustomer(), account.getCustomer().getId()),
                () -> Assertions.assertEquals(accountRequest.getAccountTypeString(), account.getAccountTypeEnum().toString()));
    }
}
