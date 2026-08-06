package mata.hoshi.app.dto.reservation.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import mata.hoshi.app.model.ReservationStatus;

@Data
public class ReservationResponse {

  private UUID id;
  private Instant createdAt;
  private ReservationStatus status;

  private UUID userId;
  private UUID projectionId;
  private Set<UUID> seatIds;
}
