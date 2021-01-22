package service;

import gifengine.mapper.GifInfoMapperImpl;
import gifengine.model.giphy.GifDto;
import gifengine.model.view.GifInfo;
import gifengine.model.view.GifResponseBody;
import gifengine.model.giphy.GiphyResponse;
import gifengine.service.GifSearchService;
import gifengine.service.GiphyCommunicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GifSearchServiceTest {
    @InjectMocks
    GifSearchService gifSearchService;

    @Mock
    GiphyCommunicationService giphyCommunicationService;

    @Mock
    GifInfoMapperImpl gifInfoMapper;


    @Test
    public void shouldReturnEmptyListWhenResultSetLessThanFive(){
        Mockito.when(giphyCommunicationService.searchByQuery(Mockito.any())).thenReturn(Mockito.any(GiphyResponse.class));
        GifResponseBody gifResponse = gifSearchService.searchGif("search");
        List<GifInfo> gifInfoList = gifResponse.getData();
        assertEquals(0 , gifInfoList.size());
    }

    @Test
    public void shouldReturnEmptyListWhenSearchTermIsEmpty(){
        GifResponseBody gifResponse = gifSearchService.searchGif("");
        List<GifInfo> gifInfoList = gifResponse.getData();
        assertEquals(0 , gifInfoList.size());
    }

    @Test
    public void shouldReturnFiveWhenResultSetEqualsFive()  {
        GifDto gifdto = GifDto.builder().url("url").id("id").build();
        //GifDto gifDto5 = GifDto.builder().url("url5").id("id5").build();
        GifDto[] gifDtos = new GifDto[]{gifdto,gifdto,gifdto,gifdto, gifdto};
        GifInfo gifInfoExpected = GifInfo.builder().gifId("id").url("url").build();
        //GifInfo gifInfoExpected5 = GifInfo.builder().gifId("id5").url("url5").build();
        GifInfo[] gifInfosExpected = new GifInfo[]{gifInfoExpected,gifInfoExpected,gifInfoExpected,gifInfoExpected,gifInfoExpected};
        List<GifInfo> gifInfoListExpected = Arrays.asList(gifInfosExpected);
        GiphyResponse giphyResponse = GiphyResponse.builder()
                .data(gifDtos)
                .build();
        String searchTerm = "search";
        Mockito.when(giphyCommunicationService.searchByQuery(searchTerm)).thenReturn(giphyResponse);
        Mockito.when(gifInfoMapper.mapGifDto( Mockito.any(GifDto.class))).thenReturn(gifInfoExpected);

        GifResponseBody gifResponseBody = gifSearchService.searchGif(searchTerm);
        List<GifInfo> gifInfoList = gifResponseBody.getData();
        assertEquals(5 , gifInfoList.size());
        gifInfoList.forEach( gifInfo -> {
            assertEquals(gifInfoExpected.getGifId() , gifInfo.getGifId());
            assertEquals(gifInfoExpected.getUrl() , gifInfo.getUrl());
        });
    }
}
