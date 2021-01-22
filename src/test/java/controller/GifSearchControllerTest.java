package controller;

import gifengine.controller.GifSearchController;
import gifengine.model.view.GifResponseBody;
import gifengine.service.GifSearchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GifSearchController.class)
@AutoConfigureMockMvc
class GifSearchControllerTest {

    @MockBean
    GifSearchService gifSearchService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHTTPRouting() throws Exception {
        Mockito.when(gifSearchService.searchGif(Mockito.anyString())).thenReturn(Mockito.any(GifResponseBody.class));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/search/hi").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testServiceGetCalled() throws Exception {
        Mockito.when(gifSearchService.searchGif(Mockito.anyString())).thenReturn(Mockito.any(GifResponseBody.class));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/search/hi").accept(MediaType.APPLICATION_JSON));
        Mockito.verify(gifSearchService,Mockito.times(1)).searchGif(Mockito.anyString());
    }

}
