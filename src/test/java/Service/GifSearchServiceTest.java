package Service;

import GIFEngine.Model.GifDto;
import GIFEngine.Model.GifInfo;
import GIFEngine.Model.GifResponse;
import GIFEngine.Model.GiphyResponse;
import GIFEngine.Service.GifSearchService;
import GIFEngine.Service.GiphyCommunicationService;
import GIFEngine.Service.GiphySearchDTOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    GiphySearchDTOMapper giphySearchDTOMapper;

    @Test
    public void shouldReturnEmptyListWhenResultSetLessThanFive(){
        GifDto gifdto = GifDto.builder().url("url").id("id").build();
        GifDto[] gifDtos = new GifDto[]{gifdto};
        GiphyResponse giphyResponse = GiphyResponse.builder()
                .data(gifDtos)
                .build();
        Mockito.when(giphyCommunicationService.pollGiphy(Mockito.any())).thenReturn(giphyResponse);
        GifResponse gifResponse = gifSearchService.searchGif("search");
        List<GifInfo> gifInfoList = gifResponse.getData();
        assertEquals(0 , gifInfoList.size());
    }

    @Test
    public void shouldReturnEmptyListWhenSearchTermIsEmpty(){
        GifDto gifdto = GifDto.builder().url("url").id("id").build();
        GifDto[] gifDtos = new GifDto[]{gifdto};
        GiphyResponse giphyResponse = GiphyResponse.builder()
                .data(gifDtos)
                .build();
        GifResponse gifResponse = gifSearchService.searchGif("");
        List<GifInfo> gifInfoList = gifResponse.getData();
        assertEquals(0 , gifInfoList.size());
    }

    @Test
    public void shouldReturnFifthWhenResultSetEqualsFive(){
        GifDto gifdto = GifDto.builder().url("url").id("id").build();
        GifDto gifDto5 = GifDto.builder().url("url5").id("id5").build();
        GifDto[] gifDtos = new GifDto[]{gifdto,gifdto,gifdto,gifdto, gifDto5};
        GifInfo gifInfoExpected = GifInfo.builder().gif_id("id5").url("url5").build();
        List<GifInfo> gifInfoListExpected = new LinkedList<>();
        gifInfoListExpected.add(gifInfoExpected);
        GiphyResponse giphyResponse = GiphyResponse.builder()
                .data(gifDtos)
                .build();
        Mockito.when(giphyCommunicationService.pollGiphy(Mockito.any())).thenReturn(giphyResponse);
        Mockito.when(giphySearchDTOMapper.gifDTOMapper(Mockito.any(GifDto.class))).thenReturn(gifInfoListExpected);

        GifResponse gifResponse = gifSearchService.searchGif("search");
        List<GifInfo> gifInfoList = gifResponse.getData();
        assertEquals(1 , gifInfoList.size());
        GifInfo gifInfo = gifInfoList.get(0);
        assertEquals("id5" , gifInfo.getGif_id());
        assertEquals("url5" , gifInfo.getUrl());
    }

}
