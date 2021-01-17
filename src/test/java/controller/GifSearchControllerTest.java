package controller;

import gifengine.controller.GifSearchController;
import gifengine.model.GifInfo;
import gifengine.model.GifResponseBody;
import gifengine.service.GifSearchService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GifSearchController.class)
@AutoConfigureMockMvc
public class GifSearchControllerTest {

    @MockBean
    GifSearchService gifSearchService;

    @Autowired
    private MockMvc mockMvc;

    private GifResponseBody gifResponseBody;

    @Before
    void setup(){
        GifInfo gifInfo = GifInfo.builder().gifId("idTest").build();
        List<GifInfo> gifInfoList = Arrays.asList(gifInfo);
        GifResponseBody gifResponseBody = GifResponseBody.builder()
                .data(gifInfoList)
                .build();
        gifResponseBody = GifResponseBody.builder().build();
    }

    @Test
    public void testHTTPRouting() throws Exception {
        Mockito.when(gifSearchService.searchGif(Mockito.anyString())).thenReturn(gifResponseBody);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/search/hi").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testServiceGetCalled() throws Exception {
        Mockito.when(gifSearchService.searchGif(Mockito.anyString())).thenReturn(gifResponseBody);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/search/hi").accept(MediaType.APPLICATION_JSON));
        Mockito.verify(gifSearchService,Mockito.times(1)).searchGif(Mockito.anyString());
    }






}
