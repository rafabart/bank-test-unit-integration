package com.invillia.banktestunitintegration.domain.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {

    private Long id;

    @NotNull(message = "Nome não pode ser nulo!")
    @NotBlank(message = "Nome não pode estar em branco!")
    private String cpf;

    @NotNull(message = "Nome não pode ser nulo!")
    @NotBlank(message = "Nome não pode estar em branco!")
    private String name;
}
