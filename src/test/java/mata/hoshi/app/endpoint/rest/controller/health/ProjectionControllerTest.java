package mata.hoshi.app.endpoint.rest.controller.health;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import mata.hoshi.app.dto.projection.request.ProjectionRequest;
import mata.hoshi.app.dto.projection.response.ProjectionResponse;
import mata.hoshi.app.mapper.ProjectionMapper;
import mata.hoshi.app.model.Projection;
import mata.hoshi.app.service.ProjectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjectionController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProjectionControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private ProjectionService projectionService;

  @MockBean private ProjectionMapper projectionMapper;

  @Test
  void shouldReturnAllProjections() throws Exception {
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

    ProjectionResponse response = new ProjectionResponse();
    response.setId(id);
    response.setDatetime(datetime);
    response.setSeatPrice(projection.seatPrice());
    response.setMovieId(projection.movieId());
    response.setRoomId(projection.roomId());

    when(projectionService.findAll()).thenReturn(List.of(projection));
    when(projectionMapper.toResponse(projection)).thenReturn(response);

    mockMvc
        .perform(get("/projections"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()))
        .andExpect(jsonPath("$[0].seatPrice").value("12.5"));
  }

  @Test
  void shouldUpdateProjection() throws Exception {
    UUID id = UUID.randomUUID();
    Instant datetime = Instant.parse("2026-08-01T10:00:00Z");

    ProjectionRequest request = new ProjectionRequest();
    request.setDatetime(datetime);
    request.setSeatPrice(new BigDecimal("15.00"));
    request.setMovieId(UUID.randomUUID());
    request.setRoomId(UUID.randomUUID());

    Projection updated =
        Projection.builder()
            .id(id)
            .datetime(request.getDatetime())
            .seatPrice(request.getSeatPrice())
            .movieId(request.getMovieId())
            .roomId(request.getRoomId())
            .build();

    ProjectionResponse response = new ProjectionResponse();
    response.setId(id);
    response.setDatetime(datetime);
    response.setSeatPrice(request.getSeatPrice());
    response.setMovieId(request.getMovieId());
    response.setRoomId(request.getRoomId());

    when(projectionService.update(id, request)).thenReturn(updated);
    when(projectionMapper.toResponse(updated)).thenReturn(response);

    mockMvc
        .perform(
            put("/projections/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()))
        .andExpect(jsonPath("$.seatPrice").value("15.0"));
  }
}
