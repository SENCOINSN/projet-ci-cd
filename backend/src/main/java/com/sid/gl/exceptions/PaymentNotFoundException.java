package com.sid.gl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(){
    }

    public PaymentNotFoundException(String msg){
        super(msg);
    }

}
