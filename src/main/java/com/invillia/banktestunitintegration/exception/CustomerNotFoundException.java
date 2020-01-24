package com.invillia.banktestunitintegration.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {

        super("Pessoa de ID " + id + " não encontrada!");
    }
}