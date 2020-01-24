package com.invillia.banktestunitintegration.exception;

public class AccountTypeNotFoundException extends RuntimeException {

    public AccountTypeNotFoundException(String message) {

        super("Tipo de conta não cadastrado: " + message);
    }
}
