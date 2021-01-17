package gifengine.service;

import gifengine.constants.AppConstants;
import gifengine.exceptions.RestTemplateException;
import gifengine.model.GiphyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public GiphyResponse searchByQuery(String searchTerm){
        //build request header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity<?> entity = new HttpEntity<>(headers);

        //build URI
        String uriString = buildSearchUriString(searchTerm);

        RestTemplate restTemplate = new RestTemplate();
        LOGGER.info("Calling Giphy to search for GIF");
        try{
            ResponseEntity<GiphyResponse> giphyResponse = restTemplate.exchange(uriString, HttpMethod.GET, entity, GiphyResponse.class);
            return giphyResponse.getBody();
        }catch(RestClientException e){
            throw new RestTemplateException(GIPHY, e.getMessage());
        }
    }

    private String buildSearchUriString(String searchTerm) {
        return UriComponentsBuilder.fromHttpUrl(giphyUrl)
                .queryParam("api_key", giphyKey)
                //set a limit to avoid over-fetching.
                .queryParam("limit", AppConstants.FETCH_SIZE)
                .queryParam("q", searchTerm).toUriString();
    }
}
