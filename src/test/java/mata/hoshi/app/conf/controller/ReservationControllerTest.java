package mata.hoshi.app.conf.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mata.hoshi.app.dto.reservation.request.ReservationRequest;
import mata.hoshi.app.dto.reservation.response.ReservationResponse;
import mata.hoshi.app.endpoint.rest.controller.health.ReservationController;
import mata.hoshi.app.mapper.ReservationMapper;
import mata.hoshi.app.model.Reservation;
import mata.hoshi.app.model.ReservationStatus;
import mata.hoshi.app.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private ReservationService reservationService;

  @MockBean private ReservationMapper reservationMapper;

  // GET /reservations     ++++++++++++

  @Test
  void shouldReturnAllReservations() throws Exception {

    UUID id = UUID.randomUUID();

    Reservation reservation =
        Reservation.builder()
            .id(id)
            .createdAt(Instant.now())
            .status(ReservationStatus.PENDING)
            .userId(UUID.randomUUID())
            .projectionId(UUID.randomUUID())
            .seatIds(Set.of(UUID.randomUUID()))
            .build();

    ReservationResponse response = new ReservationResponse();
    response.setId(id);
    response.setStatus(ReservationStatus.PENDING);
    response.setCreatedAt(reservation.createdAt());
    response.setUserId(reservation.userId());
    response.setProjectionId(reservation.projectionId());
    response.setSeatIds(reservation.seatIds());

    when(reservationService.findAll()).thenReturn(List.of(reservation));
    when(reservationMapper.toResponse(reservation)).thenReturn(response);

    mockMvc
        .perform(get("/reservations"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()))
        .andExpect(jsonPath("$[0].status").value("PENDING"));
  }

  // GET /reservations/{id}    +++++++++++

  @Test
  void shouldReturnReservationById() throws Exception {

    UUID id = UUID.randomUUID();

    Reservation reservation =
        Reservation.builder()
            .id(id)
            .createdAt(Instant.now())
            .status(ReservationStatus.SUCCESS)
            .userId(UUID.randomUUID())
            .projectionId(UUID.randomUUID())
            .seatIds(Set.of())
            .build();

    ReservationResponse response = new ReservationResponse();
    response.setId(id);
    response.setStatus(ReservationStatus.SUCCESS);
    response.setCreatedAt(reservation.createdAt());
    response.setUserId(reservation.userId());
    response.setProjectionId(reservation.projectionId());
    response.setSeatIds(reservation.seatIds());

    when(reservationService.findById(id)).thenReturn(reservation);
    when(reservationMapper.toResponse(reservation)).thenReturn(response);

    mockMvc
        .perform(get("/reservations/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()))
        .andExpect(jsonPath("$.status").value("SUCCESS"));
  }

  // PUT /reservations/{id}    ++++++++++++

  @Test
  void shouldUpdateReservation() throws Exception {

    UUID id = UUID.randomUUID();

    ReservationRequest request = new ReservationRequest();
    request.setSeatIds(Set.of(UUID.randomUUID()));

    Reservation updated =
        Reservation.builder()
            .id(id)
            .createdAt(Instant.now())
            .status(ReservationStatus.SUCCESS)
            .userId(UUID.randomUUID())
            .projectionId(UUID.randomUUID())
            .seatIds(request.getSeatIds())
            .build();

    ReservationResponse response = new ReservationResponse();
    response.setId(id);
    response.setStatus(ReservationStatus.SUCCESS);
    response.setCreatedAt(updated.createdAt());
    response.setUserId(updated.userId());
    response.setProjectionId(updated.projectionId());
    response.setSeatIds(updated.seatIds());

    when(reservationService.update(id, request)).thenReturn(updated);
    when(reservationMapper.toResponse(updated)).thenReturn(response);

    mockMvc
        .perform(
            put("/reservations/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("SUCCESS"));
  }

  // VALIDATION TEST (400)    ++++++++

  @Test
  void shouldReturn400WhenSeatIdsEmpty() throws Exception {

    UUID id = UUID.randomUUID();

    ReservationRequest request = new ReservationRequest(); // vide

    mockMvc
        .perform(
            put("/reservations/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }
}
