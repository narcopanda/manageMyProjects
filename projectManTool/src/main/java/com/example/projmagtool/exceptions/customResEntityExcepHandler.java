package com.example.projmagtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class customResEntityExcepHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdExcep projectIdExcep, WebRequest webRequest){
        var projectIdExcepRes = new ProjectIdExcepRes(projectIdExcep.getMessage());
        return new ResponseEntity<>(projectIdExcepRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException e, WebRequest webRequest){
        ProjectNotFoundExceptionRes projectNotFoundExcepRes = new ProjectNotFoundExceptionRes(e.getMessage());
        return new ResponseEntity<>(projectNotFoundExcepRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex, WebRequest request){
        UsernameAlreadyExistsRes exceptionRes = new UsernameAlreadyExistsRes(ex.getMessage());
        return new ResponseEntity<>(exceptionRes, HttpStatus.BAD_REQUEST);
    }
}
