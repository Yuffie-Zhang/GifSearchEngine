package gifengine.model;

import lombok.Data;

import java.util.Collection;
import java.util.Collections;

@Data
public class ErrorResponse {

    Collection<String> errors;

    public ErrorResponse(String error){
        this.errors = Collections.singletonList(error);
    }

}
