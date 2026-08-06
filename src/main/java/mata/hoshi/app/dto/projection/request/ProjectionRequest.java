package mata.hoshi.app.dto.projection.request;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class ProjectionRequest {

    private Instant datetime;

    private BigDecimal seatPrice;

    private UUID movieId;

    private UUID roomId;
}