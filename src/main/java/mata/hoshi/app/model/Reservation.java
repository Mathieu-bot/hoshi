package mata.hoshi.app.model;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Reservation(
    UUID id,
    Instant createdAt,
    ReservationStatus status,
    UUID userId,
    UUID projectionId,
    Set<UUID> seatIds) {}
