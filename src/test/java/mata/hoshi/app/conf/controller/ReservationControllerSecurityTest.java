package mata.hoshi.app.conf.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import mata.hoshi.app.endpoint.rest.controller.health.ReservationController;
import mata.hoshi.app.mapper.ReservationMapper;
import mata.hoshi.app.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReservationController.class)
class ReservationControllerSecurityTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ReservationService reservationService;

  @MockBean private ReservationMapper reservationMapper;

  // =========================
  //  GET /reservations
  // =========================

  @Test
  @WithMockUser(roles = "CLIENT")
  void shouldReturn403_whenClientGetAllReservations() throws Exception {
    mockMvc.perform(get("/reservations")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "EMPLOYEE")
  void shouldAllowEmployeeGetAllReservations() throws Exception {
    mockMvc.perform(get("/reservations")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "MANAGER")
  void shouldAllowManagerGetAllReservations() throws Exception {
    mockMvc.perform(get("/reservations")).andExpect(status().isOk());
  }

  // =========================
  //  GET /reservations/{id}
  // =========================

  @Test
  @WithMockUser(roles = "EMPLOYEE")
  void shouldAllowEmployeeGetById() throws Exception {
    mockMvc.perform(get("/reservations/{id}", UUID.randomUUID())).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "MANAGER")
  void shouldAllowManagerGetById() throws Exception {
    mockMvc.perform(get("/reservations/{id}", UUID.randomUUID())).andExpect(status().isOk());
  }

  // =========================
  //  PUT /reservations/{id}
  // =========================

  @Test
  @WithMockUser(roles = "CLIENT")
  void shouldAllowClientUpdateReservation() throws Exception {
    mockMvc
        .perform(
            put("/reservations/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"seatIds\": [\"123e4567-e89b-12d3-a456-426614174000\"]}"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "EMPLOYEE")
  void shouldAllowEmployeeUpdateReservation() throws Exception {
    mockMvc
        .perform(
            put("/reservations/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"seatIds\": [\"123e4567-e89b-12d3-a456-426614174000\"]}"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "MANAGER")
  void shouldAllowManagerUpdateReservation() throws Exception {
    mockMvc
        .perform(
            put("/reservations/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"seatIds\": [\"123e4567-e89b-12d3-a456-426614174000\"]}"))
        .andExpect(status().isOk());
  }

  // =========================
  //  NOT AUTHENTICATED
  // =========================

  @Test
  void shouldReturn401_whenNotAuthenticated() throws Exception {
    mockMvc.perform(get("/reservations")).andExpect(status().isUnauthorized());
  }
}
