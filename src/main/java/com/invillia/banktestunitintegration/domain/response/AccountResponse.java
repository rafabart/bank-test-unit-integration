package com.invillia.banktestunitintegration.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private Long id;

    private String numberAccount;

    private String agency;

    private Double balance;

    private Double limitAccount;

    private String accountTipyString;

    private Long idPerson;

    private String createdAt;

    private String updatedAt;
}
