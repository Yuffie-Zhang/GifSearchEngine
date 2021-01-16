package GIFEngine.Service;

import GIFEngine.Model.GifInfo;
import GIFEngine.Model.GifResponse;
import GIFEngine.Model.GiphyResponse;
import GIFEngine.Model.GifDto;
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


    @Autowired
    public GifSearchService(GiphyCommunicationService giphyCommunicationService, GiphySearchDTOMapper giphySearchDTOMapper) {
        this.giphyCommunicationService = giphyCommunicationService;
        this.giphySearchDTOMapper = giphySearchDTOMapper;
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
        GiphyResponse giphyResponse = giphyCommunicationService.pollGiphy(searchTerm);


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
