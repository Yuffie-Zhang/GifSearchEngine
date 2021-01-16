package gifengine.service;

import gifengine.constants.AppConstants;
import gifengine.exceptions.RestTemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/*
* This service will make GET call to GIPHY search API to retrieve data,
* */
@Service
public class GiphyCommunicationService {

    @Value("${giphy.url}")
    private String giphyUrl;

    @Value("${giphy.key}")
    private String giphyKey;



    private static final String GIPHY = "GIPHY";

    private static final Logger LOGGER = LoggerFactory.getLogger(GiphyCommunicationService.class);

    public String pollGiphy(String searchTerm){
        //build request header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity<?> entity = new HttpEntity<>(headers);

        //build URI
        String uriString = buildUriString(searchTerm);

        RestTemplate restTemplate = new RestTemplate();
        LOGGER.info("Calling Giphy to search for GIF");
        ResponseEntity<String> giphyResponseString = null;
        try{
            giphyResponseString = restTemplate.exchange(uriString, HttpMethod.GET, entity, String.class);
            String body = giphyResponseString.getBody();
            HttpStatus statusCode = giphyResponseString.getStatusCode();
            if(statusCode.equals(HttpStatus.OK)){
                return body;
            }
        }catch(RestClientException e){
            throw new RestTemplateException(GIPHY, e.getMessage());
        }
        return giphyResponseString.getBody();
    }

    private String buildUriString(String searchTerm) {
        return UriComponentsBuilder.fromHttpUrl(giphyUrl)
                .queryParam("api_key", giphyKey)
                //set a limit to avoid over-fetching.
                .queryParam("limit", AppConstants.FETCH_SIZE)
                .queryParam("q", searchTerm).toUriString();
    }
}
