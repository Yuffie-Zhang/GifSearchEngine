package gifengine.service;

import gifengine.mapper.GifInfoMapper;
import gifengine.model.GifInfo;
import gifengine.model.GifResponseBody;
import gifengine.model.GiphyResponse;
import gifengine.model.GifDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/*
This service gather data from GIPHY and prepare response to GifSearchController
*/
@Service
@RequiredArgsConstructor
public class GifSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GifSearchService.class);

    private final GiphyCommunicationService giphyCommunicationService;

    private final GifInfoMapper gifInfoMapper;


    /*
    The function is supposed to return a GifResponse object with input of searchTerm.
     */
    public GifResponseBody searchGif(String searchTerm){
        List<GifInfo> gifInfoList = new LinkedList<>();
        GifResponseBody gifResponseBody = GifResponseBody.builder()
                .data(gifInfoList)
                .build();
        //return empty list if searching with an empty search term
        if(searchTerm.isEmpty()){
            return gifResponseBody;
        }
        LOGGER.info("Start searching for GIF");
        //get response from Giphy API
        GiphyResponse giphyResponse = giphyCommunicationService.searchByQuery(searchTerm);

        //prepare response using Giphy data
        if (Objects.nonNull(giphyResponse) && giphyResponse.getData().length >= 5) {
            //only return the fifth data if result set >=5
            GifDto[] gifs = giphyResponse.getData();
            GifDto fifth = gifs[4];
            gifInfoList.add(gifInfoMapper.mapGifDto(fifth));
        }
        gifResponseBody.setData(gifInfoList);
        return gifResponseBody;
    }

}
