package gifengine.mapper;

import gifengine.model.GifDto;
import gifengine.model.GifInfo;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultMapperConfig.class
)
public interface GifInfoMapper {

    GifInfo mapGifDto(GifDto gifDto);
}
