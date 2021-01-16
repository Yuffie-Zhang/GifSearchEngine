package GIFEngine.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GifInfo {
    String gif_id;
    String url;
}
