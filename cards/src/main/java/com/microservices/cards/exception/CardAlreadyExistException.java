package com.microservices.cards.exception;

public class CardAlreadyExistException extends Exception{
    public CardAlreadyExistException(String message) {
        super(message);
    }
}
