package Controller;

import gifengine.controller.GifSearchController;
import gifengine.model.GifResponseBody;
import gifengine.service.GifSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GifSearchControllerTest {
    @InjectMocks
    GifSearchController gifSearchController;

    @Mock
    GifSearchService gifSearchService;

    @Test
    public void testSearchGifSuccessful(){
        GifResponseBody gifResponseBody = GifResponseBody.builder().build();
        Mockito.when(gifSearchService.searchGif(Mockito.anyString())).thenReturn(gifResponseBody);
        ResponseEntity<GifResponseBody> gifResponseResponseEntity = gifSearchController.searchGif("");
        assertEquals(HttpStatus.OK, gifResponseResponseEntity.getStatusCode());
        assertEquals(gifResponseBody, gifResponseResponseEntity.getBody());
    }





}
