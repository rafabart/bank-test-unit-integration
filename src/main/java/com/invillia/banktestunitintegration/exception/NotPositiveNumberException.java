package com.invillia.banktestunitintegration.exception;

public class NotPositiveNumberException extends RuntimeException {

    public NotPositiveNumberException() {

        super("O Valor do saque deve ser positivo!");
    }
}