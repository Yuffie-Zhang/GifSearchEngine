package gifengine.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GiphyResponse {
    GifDto[] data;
    Pagination pagination;
    Meta meta;
}
