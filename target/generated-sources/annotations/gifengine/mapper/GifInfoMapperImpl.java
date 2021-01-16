package gifengine.mapper;

import gifengine.model.GifDto;
import gifengine.model.GifInfo;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-16T12:49:34-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
@Component
public class GifInfoMapperImpl implements GifInfoMapper {

    @Override
    public GifInfo mapGifDto(GifDto gifDto) {
        if ( gifDto == null ) {
            return null;
        }

        String arg0 = null;
        String arg1 = null;

        GifInfo gifInfo = new GifInfo( arg0, arg1 );

        gifInfo.setGifId( gifDto.getGifId() );
        gifInfo.setUrl( gifDto.getUrl() );

        return gifInfo;
    }
}
