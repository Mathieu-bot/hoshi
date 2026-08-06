package mata.hoshi.app.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import mata.hoshi.app.dto.reservation.request.ReservationRequest;
import mata.hoshi.app.dto.reservation.response.ReservationResponse;
import mata.hoshi.app.model.Reservation;
import mata.hoshi.app.model.ReservationStatus;
import org.junit.jupiter.api.Test;

class ReservationMapperTest {

  private final ReservationMapper mapper = new ReservationMapperImpl();

  @Test
  void toResponseMapsAllFields() {
    Reservation reservation =
        Reservation.builder()
            .id(UUID.randomUUID())
            .createdAt(Instant.parse("2026-08-01T10:00:00Z"))
            .status(ReservationStatus.SUCCESS)
            .userId(UUID.randomUUID())
            .projectionId(UUID.randomUUID())
            .seatIds(Set.of(UUID.randomUUID()))
            .build();

    ReservationResponse response = mapper.toResponse(reservation);

    assertEquals(reservation.id(), response.getId());
    assertEquals(reservation.createdAt(), response.getCreatedAt());
    assertEquals(reservation.status(), response.getStatus());
    assertEquals(reservation.userId(), response.getUserId());
    assertEquals(reservation.projectionId(), response.getProjectionId());
    assertEquals(reservation.seatIds(), response.getSeatIds());
  }

  @Test
  void toEntityMapsRequestAndIgnoresManagedFields() {
    ReservationRequest request = new ReservationRequest();
    request.setSeatIds(Set.of(UUID.randomUUID()));
    request.setProjectionId(UUID.randomUUID());

    Reservation reservation = mapper.toEntity(request);

    assertEquals(request.getSeatIds(), reservation.seatIds());
    assertEquals(request.getProjectionId(), reservation.projectionId());
    assertNull(reservation.id());
    assertNull(reservation.createdAt());
    assertNull(reservation.status());
    assertNull(reservation.userId());
  }
}
