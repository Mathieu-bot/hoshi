package mata.hoshi.app.dto.projection.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class ProjectionResponse {

    private UUID id;

    private Instant datetime;

    private BigDecimal seatPrice;

    private UUID movieId;

    private UUID roomId;
}