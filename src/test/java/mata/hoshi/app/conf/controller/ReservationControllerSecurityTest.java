package mata.hoshi.app.conf.controller;

import mata.hoshi.app.endpoint.rest.controller.health.ReservationController;
import mata.hoshi.app.mapper.ReservationMapper;
import mata.hoshi.app.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ReservationMapper reservationMapper;


// =========================
//  GET /reservations
// =========================

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturn403_whenClientGetAllReservations() throws Exception {
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void shouldAllowEmployeeGetAllReservations() throws Exception {
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void shouldAllowManagerGetAllReservations() throws Exception {
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk());
    }


// =========================
//  GET /reservations/{id}
// =========================

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void shouldAllowEmployeeGetById() throws Exception {
        mockMvc.perform(get("/reservations/{id}", "123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void shouldAllowManagerGetById() throws Exception {
        mockMvc.perform(get("/reservations/{id}", "123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isOk());
    }


// =========================
//  PUT /reservations/{id}
// =========================

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldAllowClientUpdateReservation() throws Exception {
        mockMvc.perform(put("/reservations/{id}", "123e4567-e89b-12d3-a456-426614174000")
                        .contentType("application/json")
                        .content("{\"seatIds\": []}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void shouldAllowEmployeeUpdateReservation() throws Exception {
        mockMvc.perform(put("/reservations/{id}", "123e4567-e89b-12d3-a456-426614174000")
                        .contentType("application/json")
                        .content("{\"seatIds\": []}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void shouldAllowManagerUpdateReservation() throws Exception {
        mockMvc.perform(put("/reservations/{id}", "123e4567-e89b-12d3-a456-426614174000")
                        .contentType("application/json")
                        .content("{\"seatIds\": []}"))
                .andExpect(status().isOk());
    }


// =========================
//  NOT AUTHENTICATED
// =========================

    @Test
    void shouldReturn401_whenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isUnauthorized());
    }


}
