package mata.hoshi.app.conf.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import mata.hoshi.app.dto.reservation.response.ReservationResponse;
import mata.hoshi.app.endpoint.rest.controller.health.ReservationController;
import mata.hoshi.app.mapper.ReservationMapper;
import mata.hoshi.app.model.Reservation;
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

  @MockBean private ReservationService reservationService;

  @MockBean private ReservationMapper reservationMapper;

  @Test
  void shouldReturnAllReservations() throws Exception {

    UUID id = UUID.randomUUID();

    Reservation reservation = new Reservation();
    reservation.setId(id);

    ReservationResponse response = new ReservationResponse();
    response.setId(id);
    response.setStatus("PENDING");
    response.setCreatedAt(Instant.now());

    when(reservationService.findAll()).thenReturn(List.of(reservation));
    when(reservationMapper.toResponse(reservation)).thenReturn(response);

    mockMvc
        .perform(get("/reservations").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()))
        .andExpect(jsonPath("$[0].status").value("PENDING"));
  }

  @Test
  void shouldReturnReservationById() throws Exception {
    UUID id = UUID.randomUUID();
    Reservation reservation = new Reservation();
    reservation.setId(id);
    ReservationResponse response = new ReservationResponse();
    response.setId(id);
    response.setStatus("PENDING");
    response.setCreatedAt(Instant.now());
    when(reservationService.findById(id)).thenReturn(reservation);
    when(reservationMapper.toResponse(reservation)).thenReturn(response);
    mockMvc
        .perform(get("/reservations/{id}", id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()))
        .andExpect(jsonPath("$.status").value("PENDING"));
  }

  @Test
  void shouldReturn404WhenReservationNotFound() throws Exception {
    UUID id = UUID.randomUUID();
    when(reservationService.findById(id)).thenThrow(new RuntimeException("Reservation not found"));
    mockMvc
        .perform(get("/reservations/{id}", id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
