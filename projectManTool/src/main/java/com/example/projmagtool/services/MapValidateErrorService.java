package com.example.projmagtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidateErrorService {

    public ResponseEntity<?> MapValidateService(BindingResult result){
        if(result.hasErrors()){
            var errorMap = new HashMap<String, String>();
            for(FieldError err : result.getFieldErrors()){
                errorMap.put(err.getField(), err.getDefaultMessage());
            }
            return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
