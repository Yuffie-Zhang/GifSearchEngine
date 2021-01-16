package GIFEngine.Service;

import GIFEngine.Model.GifInfo;
import GIFEngine.Model.GifDto;
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
                .gif_id(Optional.ofNullable(giphyResponse.getId()).orElse(""))
                .url(Optional.ofNullable(giphyResponse.getUrl()).orElse(""))
                .build();
        List<GifInfo> gifInfoList = new LinkedList<>();
        gifInfoList.add(gifInfo);
        return gifInfoList;
    }
}
