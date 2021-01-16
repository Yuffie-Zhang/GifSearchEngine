package Service;

import GIFEngine.Model.GifDto;
import GIFEngine.Model.GifInfo;
import GIFEngine.Service.GiphySearchDTOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GiphyCommunicationServiceTest {

    @InjectMocks
    GiphySearchDTOMapper giphySearchDTOMapper;

    @Test
    public void mapToGifInfo(){
        GifDto gifdto = GifDto.builder()
                .url("url")
                .id("id")
                .build();
        List<GifInfo> gifInfoList = giphySearchDTOMapper.gifDTOMapper(gifdto);
        assertEquals(1,gifInfoList.size());
        GifInfo gifInfo = gifInfoList.get(0);
        assertEquals("id" , gifInfo.getGif_id());
        assertEquals("url" , gifInfo.getUrl());

    }
}
