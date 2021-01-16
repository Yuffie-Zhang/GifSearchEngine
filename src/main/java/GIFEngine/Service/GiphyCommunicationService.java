package GIFEngine.Service;

import GIFEngine.Constants.AppConstants;
import GIFEngine.Model.GiphyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Value("${giphy.fetch.size}")
    private int fetchSize;

    private ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(GiphyCommunicationService.class);

    @Autowired
    public GiphyCommunicationService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public GiphyResponse pollGiphy(String searchTerm){
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
        }catch(RestClientException e){
            LOGGER.error("Failed to call GIPHY");
            throw e;
        }

        if (giphyResponseString.getStatusCode().equals(HttpStatus.OK)) {
            String body = giphyResponseString.getBody();

            GiphyResponse giphyResponse = null;
            try{
                giphyResponse = objectMapper.readValue(body, GiphyResponse.class);
            }catch(JsonProcessingException e){
                LOGGER.error("Failed to read value from GIPHY response");
            }
            return giphyResponse;
        } else {
            //throw RunTimeException if Giphy call is not returned with 200OK
            throw new RuntimeException("Giphy Call Failure");
        }
    }

    private String buildUriString(String searchTerm) {
        return UriComponentsBuilder.fromHttpUrl(giphyUrl)
                .queryParam("api_key", giphyKey)
                //set a limit to avoid over-fetching.
                .queryParam("limit", AppConstants.fetchSize)
                .queryParam("q", searchTerm).toUriString();
    }
}
