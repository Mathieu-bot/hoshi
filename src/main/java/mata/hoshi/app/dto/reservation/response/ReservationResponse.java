package mata.hoshi.app.dto.reservation.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ReservationResponse {
  private UUID id;
  private Instant createdAt;
  private String status;
  private UUID userId;
  private UUID projectionId;
  private List<UUID> seatIds;
}
