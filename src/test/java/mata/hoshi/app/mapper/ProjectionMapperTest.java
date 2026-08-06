package mata.hoshi.app.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import mata.hoshi.app.dto.projection.request.ProjectionRequest;
import mata.hoshi.app.dto.projection.response.ProjectionResponse;
import mata.hoshi.app.model.Projection;
import org.junit.jupiter.api.Test;

class ProjectionMapperTest {

  private final ProjectionMapper mapper = new ProjectionMapperImpl();

  @Test
  void toResponseMapsAllFields() {
    UUID id = UUID.randomUUID();
    Instant datetime = Instant.parse("2026-08-01T10:00:00Z");

    Projection projection =
        Projection.builder()
            .id(id)
            .datetime(datetime)
            .seatPrice(new BigDecimal("12.50"))
            .movieId(UUID.randomUUID())
            .roomId(UUID.randomUUID())
            .build();

    ProjectionResponse response = mapper.toResponse(projection);

    assertEquals(projection.id(), response.getId());
    assertEquals(projection.datetime(), response.getDatetime());
    assertEquals(projection.seatPrice(), response.getSeatPrice());
    assertEquals(projection.movieId(), response.getMovieId());
    assertEquals(projection.roomId(), response.getRoomId());
  }

  @Test
  void toModelMapsRequestAndIgnoresId() {
    ProjectionRequest request = new ProjectionRequest();
    request.setDatetime(Instant.parse("2026-08-01T10:00:00Z"));
    request.setSeatPrice(new BigDecimal("12.50"));
    request.setMovieId(UUID.randomUUID());
    request.setRoomId(UUID.randomUUID());

    Projection projection = mapper.toModel(request);

    assertNull(projection.id());
    assertEquals(request.getDatetime(), projection.datetime());
    assertEquals(request.getSeatPrice(), projection.seatPrice());
    assertEquals(request.getMovieId(), projection.movieId());
    assertEquals(request.getRoomId(), projection.roomId());
  }
}
