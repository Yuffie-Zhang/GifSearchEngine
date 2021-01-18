package gifengine.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RestTemplateException extends RuntimeException{

    final String api;

    final HttpStatus statusCode;

    final String error;

}


