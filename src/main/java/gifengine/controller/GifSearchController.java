package gifengine.controller;

import gifengine.model.GifResponse;
import gifengine.service.GifSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
* This endpoint aims to return information of GIF.
* Input -- search term
* Output: if result set is >=5, return the {gif_key, url} of the 5th GIF. Otherwise, return an empty set.
* */

@RestController
public class GifSearchController {

    private static final Logger LOGGER  = LoggerFactory.getLogger(GifSearchController.class);

    private final GifSearchService gifSearchService;

    @Autowired
    public GifSearchController(GifSearchService gifSearchService){
        this.gifSearchService = gifSearchService;
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<GifResponse> searchGif(@PathVariable("searchTerm") String searchTerm){
        LOGGER.info("You're search for GIF with search term {}",searchTerm);
        return new ResponseEntity<>(gifSearchService.searchGif(searchTerm), HttpStatus.OK);
    }

}
