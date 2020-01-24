package com.invillia.banktestunitintegration.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long id) {

        super("Conta de ID " + id + " não encontrada!");
    }
}