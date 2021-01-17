package gifengine.mapper;

import gifengine.model.GifDto;
import gifengine.model.GifInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = DefaultMapperConfig.class
)
public interface GifInfoMapper {

    @Mapping(target ="gifId", source = "id")
    GifInfo mapGifDto(GifDto gifDto);
}
