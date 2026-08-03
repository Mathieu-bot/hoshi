package mata.hoshi.app.dto.reservation.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class ReservationRequest {

  @NotEmpty private Set<UUID> seatIds;

  private UUID projectionId;
}
