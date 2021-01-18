package service;

import gifengine.mapper.GifInfoMapperImpl;
import gifengine.model.GifDto;
import gifengine.model.GifInfo;
import gifengine.model.GifResponseBody;
import gifengine.model.GiphyResponse;
import gifengine.service.GifSearchService;
import gifengine.service.GiphyCommunicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
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
    public void shouldReturnFifthWhenResultSetEqualsFive()  {
        GifDto gifdto = GifDto.builder().url("url").id("id").build();
        GifDto gifDto5 = GifDto.builder().url("url5").id("id5").build();
        GifDto[] gifDtos = new GifDto[]{gifdto,gifdto,gifdto,gifdto, gifDto5};
        GifInfo gifInfoExpected = GifInfo.builder().gifId("id5").url("url5").build();
        List<GifInfo> gifInfoListExpected = new LinkedList<>();
        gifInfoListExpected.add(gifInfoExpected);
        GiphyResponse giphyResponse = GiphyResponse.builder()
                .data(gifDtos)
                .build();
        Mockito.when(giphyCommunicationService.searchByQuery(Mockito.any())).thenReturn(giphyResponse);
        Mockito.when(gifInfoMapper.mapGifDto( Mockito.any(GifDto.class))).thenReturn(gifInfoExpected);

        GifResponseBody gifResponseBody = gifSearchService.searchGif("search");
        List<GifInfo> gifInfoList = gifResponseBody.getData();
        assertEquals(1 , gifInfoList.size());
        GifInfo gifInfo = gifInfoList.get(0);
        assertEquals("id5" , gifInfo.getGifId());
        assertEquals("url5" , gifInfo.getUrl());
    }
}
