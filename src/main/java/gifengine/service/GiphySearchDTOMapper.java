package gifengine.service;

import gifengine.model.GifInfo;
import gifengine.model.GifDto;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/*
 * This service will map GIFDTO <-- DTO that we received from Giphy,
 * to,
 * List<GifInfo> <--Object that we need to return.
 * */
@Service
public class GiphySearchDTOMapper {

    public List<GifInfo> gifDTOMapper(GifDto giphyResponse){
        GifInfo gifInfo = GifInfo.builder()
                .gifId(Optional.ofNullable(giphyResponse.getGifId()).orElse(""))
                .url(Optional.ofNullable(giphyResponse.getUrl()).orElse(""))
                .build();
        List<GifInfo> gifInfoList = new LinkedList<>();
        gifInfoList.add(gifInfo);
        return gifInfoList;
    }
}
