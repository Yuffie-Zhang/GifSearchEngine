package gifengine.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GifResponse {

    private List<GifInfo> data;

}
