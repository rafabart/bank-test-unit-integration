package com.invillia.banktestunitintegration.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Long id;

    private String cpf;

    private String name;

    private String createdAt;

    private String updatedAt;
}
