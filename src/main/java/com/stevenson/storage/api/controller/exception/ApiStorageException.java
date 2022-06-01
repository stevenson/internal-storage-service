package com.stevenson.storage.api.controller.exception;

public class ApiStorageException extends RuntimeException{

    public ApiStorageException(String message){
        super(message);
    }

    public ApiStorageException(String message, Throwable cause){
        super(message, cause);
    }

}
