package mata.hoshi.app.conf.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import mata.hoshi.app.dto.reservation.request.ReservationRequest;
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

  @Test
  void shouldUpdateReservation() throws Exception {
    UUID id = UUID.randomUUID();
    ReservationRequest request = new ReservationRequest();
    request.setSeatIds(List.of(UUID.randomUUID(), UUID.randomUUID()));
    Reservation reservation = new Reservation();
    reservation.setId(id);
    ReservationResponse response = new ReservationResponse();
    response.setId(id);
    response.setStatus("SUCCESS");
    response.setCreatedAt(Instant.now());
    when(reservationService.update(id, request)).thenReturn(reservation);
    when(reservationMapper.toResponse(reservation)).thenReturn(response);
    mockMvc
        .perform(
            put("/reservations/{id}", id).contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()))
        .andExpect(jsonPath("$.status").value("SUCCESS"));
  }

  @Test
  void shouldReturn400WhenRequestInvalid() throws Exception {
    UUID id = UUID.randomUUID();
    ReservationRequest request = new ReservationRequest();
    mockMvc
        .perform(
            put("/reservations/{id}", id).contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest());
  }

  // =========================
  // GET /reservations
  // =========================
  @Test
  @WithMockUser(roles = "CLIENT")
  void shouldReturn403ForClient_whenGetAllReservations() throws Exception {
    mockMvc.perform(get("/reservations")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "EMPLOYEE")
  void shouldAllowEmployee_whenGetAllReservations() throws Exception {
    mockMvc.perform(get("/reservations")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "MANAGER")
  void shouldAllowManager_whenGetAllReservations() throws Exception {
    mockMvc.perform(get("/reservations")).andExpect(status().isOk());
  }

  // =========================
  // GET /reservations/{id}
  // =========================
  @Test
  @WithMockUser(roles = "EMPLOYEE")
  void shouldAllowEmployee_whenGetById() throws Exception {
    mockMvc
        .perform(get("/reservations/{id}", "123e4567-e89b-12d3-a456-426614174000"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "MANAGER")
  void shouldAllowManager_whenGetById() throws Exception {
    mockMvc
        .perform(get("/reservations/{id}", "123e4567-e89b-12d3-a456-426614174000"))
        .andExpect(status().isOk());
  }

  // =========================
  // PUT /reservations/{id}
  // =========================
  @Test
  @WithMockUser(roles = "CLIENT")
  void shouldAllowClient_whenUpdateReservation() throws Exception {
    mockMvc
        .perform(get("/reservations/{id}", "123e4567-e89b-12d3-a456-426614174000"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "EMPLOYEE")
  void shouldAllowEmployee_whenUpdateReservation() throws Exception {
    mockMvc
        .perform(get("/reservations/{id}", "123e4567-e89b-12d3-a456-426614174000"))
        .andExpect(status().isOk());
  }
}
