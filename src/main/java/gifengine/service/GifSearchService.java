package gifengine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gifengine.model.GifInfo;
import gifengine.model.GifResponse;
import gifengine.model.GiphyResponse;
import gifengine.model.GifDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/*
This service gather data from GIPHY and prepare response to GifSearchController
*/
@Service
public class GifSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GifSearchService.class);

    private final GiphyCommunicationService giphyCommunicationService;

    private final GiphySearchDTOMapper giphySearchDTOMapper;

    private ObjectMapper objectMapper;


    @Autowired
    public GifSearchService(GiphyCommunicationService giphyCommunicationService, GiphySearchDTOMapper giphySearchDTOMapper,
                            ObjectMapper objectMapper) {
        this.giphyCommunicationService = giphyCommunicationService;
        this.giphySearchDTOMapper = giphySearchDTOMapper;
        this.objectMapper = objectMapper;
    }

    /*
    The function is supposed to return a GifResponse object with input of searchTerm.
     */
    public GifResponse searchGif(String searchTerm) {
        List<GifInfo> gifInfoList = new LinkedList<>();
        GifResponse gifResponse = GifResponse.builder()
                .data(gifInfoList)
                .build();
        //return empty list if searching with an empty search term
        if(searchTerm.isEmpty()){
            return gifResponse;
        }
        LOGGER.info("Start searching for GIF");
        //get response from Giphy API
        GiphyResponse giphyResponse = null;
        try{
            giphyResponse = objectMapper.readValue(giphyCommunicationService.pollGiphy(searchTerm), GiphyResponse.class);
        }catch(JsonProcessingException e){
            LOGGER.error("Failed to read value from external response");
        }

        //prepare response using Giphy data
        if (Objects.nonNull(giphyResponse) && giphyResponse.getData().length >= 5) {
            //only return the fifth data if result set >=5
            GifDto[] gifs = giphyResponse.getData();
            GifDto fifth = gifs[4];
            gifInfoList = giphySearchDTOMapper.gifDTOMapper(fifth);
        }
        gifResponse.setData(gifInfoList);
        return gifResponse;
    }

}
