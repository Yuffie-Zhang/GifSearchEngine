package gifengine.mapper;

import gifengine.model.giphy.GifDto;
import gifengine.model.view.GifInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = DefaultMapperConfig.class
)
public interface GifInfoMapper {

    @Mapping(target ="gifId", source = "id")
    GifInfo mapGifDto(GifDto gifDto);
}
