package gifengine.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestTemplateException extends RuntimeException{

    final String api;

    final String error;

}


