package com.invillia.banktestunitintegration.domain;

import com.invillia.banktestunitintegration.domain.enums.AccountTipyEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Account extends IdAbstract<Long> {

    @Column(nullable = false, length = 6)
    private String numberAccount;

    @Column(nullable = false, length = 4)
    private String agency;

    @Column(nullable = false, precision = 10, scale = 2, columnDefinition = "double default 0")
    private Double balance;

    @PositiveOrZero(message = "Limite não pode ser negativo!")
    @Column(nullable = false, precision = 10, scale = 2, columnDefinition = "double default 0")
    private Double limitAccount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountTipyEnum accountTipyEnum;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_person", nullable = false)
    private Person person;
}