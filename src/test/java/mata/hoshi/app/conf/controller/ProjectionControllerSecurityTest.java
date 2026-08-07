package mata.hoshi.app.conf.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import mata.hoshi.app.endpoint.rest.controller.health.ProjectionController;
import mata.hoshi.app.mapper.ProjectionMapper;
import mata.hoshi.app.service.ProjectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjectionController.class)
class ProjectionControllerSecurityTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ProjectionService projectionService;

  @MockBean private ProjectionMapper projectionMapper;

  // =========================
  //  GET /projections
  // =========================

  @Test
  @WithMockUser(roles = "CLIENT")
  void shouldAllowClientGetAllProjections() throws Exception {
    mockMvc.perform(get("/projections")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "EMPLOYEE")
  void shouldAllowEmployeeGetAllProjections() throws Exception {
    mockMvc.perform(get("/projections")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "MANAGER")
  void shouldAllowManagerGetAllProjections() throws Exception {
    mockMvc.perform(get("/projections")).andExpect(status().isOk());
  }

  // =========================
  //  PUT /projections/{id}
  // =========================

  @Test
  @WithMockUser(roles = "CLIENT")
  void shouldReturn403_whenClientUpdateProjection() throws Exception {
    mockMvc
        .perform(
            put("/projections/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(projectionBody()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "EMPLOYEE")
  void shouldReturn403_whenEmployeeUpdateProjection() throws Exception {
    mockMvc
        .perform(
            put("/projections/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(projectionBody()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "MANAGER")
  void shouldAllowManagerUpdateProjection() throws Exception {
    mockMvc
        .perform(
            put("/projections/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(projectionBody()))
        .andExpect(status().isOk());
  }

  // =========================
  //  NOT AUTHENTICATED
  // =========================

  @Test
  void shouldReturn401_whenNotAuthenticated() throws Exception {
    mockMvc.perform(get("/projections")).andExpect(status().isUnauthorized());
  }

  private String projectionBody() {
    return "{"
        + "\"datetime\":\"2026-08-10T10:00:00Z\","
        + "\"seatPrice\":\"12.50\","
        + "\"movieId\":\"123e4567-e89b-12d3-a456-426614174001\","
        + "\"roomId\":\"123e4567-e89b-12d3-a456-426614174002\""
        + "}";
  }
}
