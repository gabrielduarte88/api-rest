package com.zup.comics.comicsmanager.exceptions;

public class MarvelApiException extends Exception {
    public MarvelApiException() {}

    public MarvelApiException(String message) {
        super(message);
    }

    public MarvelApiException(Throwable cause) {
        super(cause);
    }

    public MarvelApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarvelApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
