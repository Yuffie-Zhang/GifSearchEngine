package gifengine.model.view;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GifResponseBody {

    private List<GifInfo> data;

}
