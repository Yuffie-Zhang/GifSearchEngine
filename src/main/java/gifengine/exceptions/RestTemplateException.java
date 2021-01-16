package gifengine.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestTemplateException extends RuntimeException{

    private String api;

    private String error;

}


