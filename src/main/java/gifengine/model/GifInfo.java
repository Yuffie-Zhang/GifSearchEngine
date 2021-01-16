package gifengine.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GifInfo {
    String gifId;
    String url;
}
