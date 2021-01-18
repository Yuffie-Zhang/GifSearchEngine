package gifengine.exceptionhandler;

import gifengine.constants.AppConstants;
import gifengine.exceptions.RestTemplateException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class RestTemplateExceptionHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {

                String errorMessage = reader.lines().collect(Collectors.joining(""));

                throw new RestTemplateException(AppConstants.GIPHY, response.getStatusCode(), errorMessage);
            }
        }else{
            throw new RestTemplateException(AppConstants.GIPHY, response.getStatusCode(), "errorMessage");
        }
    }
}