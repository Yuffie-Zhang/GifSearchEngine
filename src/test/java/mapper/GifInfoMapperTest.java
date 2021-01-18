package mapper;

import gifengine.mapper.GifInfoMapper;
import gifengine.model.GifDto;
import gifengine.model.GifInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class GifInfoMapperTest {

    GifInfoMapper gifInfoMapper = Mappers.getMapper(GifInfoMapper.class);


    @Test
    public void mapToGifInfoTest(){
        GifDto gifdto = GifDto.builder()
                .url("url")
                .id("id")
                .build();
        GifInfo gifInfo = gifInfoMapper.mapGifDto(gifdto);
        assertEquals("id" , gifInfo.getGifId());
        assertEquals("url" , gifInfo.getUrl());

    }

}
