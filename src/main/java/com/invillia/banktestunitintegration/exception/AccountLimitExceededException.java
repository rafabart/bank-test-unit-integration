package com.invillia.banktestunitintegration.exception;

public class AccountLimitExceededException extends RuntimeException {

    public AccountLimitExceededException(Double limitAccount) {

        super("Limite de R$ " + limitAccount + " excedido!");
    }
}