package gifengine.controller;

import gifengine.exceptions.RestTemplateException;
import gifengine.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOGGER  = LoggerFactory.getLogger(GifSearchController.class);


    @ExceptionHandler(value = RestTemplateException.class)
    public final ResponseEntity<ErrorResponse> handleRestTemplateException(RestTemplateException ex, WebRequest request){
        LOGGER.error("A rest template error happened: {}", ex.toString());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
