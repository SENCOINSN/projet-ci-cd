package com.sid.gl.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentUtils {
    public static<T> T convertJson(String str, Class<T> elementClass){
        ObjectMapper mapper =
                new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        T dto = null;
        try{
            dto =  mapper.readValue(str,  elementClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  dto;
    }

}
