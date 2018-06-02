package com.accedia.tuneathon.flutter.webservices.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleMissingQueryParameterException(IllegalArgumentException e) {
        return e.getMessage();
    }
//
//    @ExceptionHandler(DateFormatException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ValidationError handleWrongDateQueryParameterException(DateFormatException e) {
//        LOGGER.error(e.getMessage(), e);
//        return new ValidationError(e.getClass().getSimpleName(), e.getMessage());
//    }
//
//    @ExceptionHandler({OptimumDispatchException.class, EtelecException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ValidationError handleOptimumDispatchException(OptimumDispatchException e) {
//        LOGGER.error(e.getMessage(), e);
//        return new ValidationError(e.getClass().getSimpleName(), e.getMessage());
//    }
}