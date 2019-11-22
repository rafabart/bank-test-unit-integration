package com.invillia.banktestunitintegration.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends IdAbstract<Long> {

    @Column(nullable = false, length = 14)
    private String cpf;

    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;
}


