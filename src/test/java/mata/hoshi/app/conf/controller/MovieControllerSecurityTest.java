package mata.hoshi.app.conf.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mata.hoshi.app.endpoint.rest.controller.health.MovieController;
import mata.hoshi.app.mapper.MovieMapper;
import mata.hoshi.app.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MovieController.class)
class MovieControllerSecurityTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private MovieService movieService;

  @MockBean private MovieMapper movieMapper;

  // =========================
  //  PUT /movies
  // =========================

  @Test
  @WithMockUser(roles = "CLIENT")
  void shouldReturn403_whenClientUpdateMovie() throws Exception {
    mockMvc
        .perform(put("/movies").contentType(MediaType.APPLICATION_JSON).content(movieBody()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "EMPLOYEE")
  void shouldReturn403_whenEmployeeUpdateMovie() throws Exception {
    mockMvc
        .perform(put("/movies").contentType(MediaType.APPLICATION_JSON).content(movieBody()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "MANAGER")
  void shouldAllowManagerUpdateMovie() throws Exception {
    mockMvc
        .perform(put("/movies").contentType(MediaType.APPLICATION_JSON).content(movieBody()))
        .andExpect(status().isOk());
  }

  // =========================
  //  NOT AUTHENTICATED
  // =========================

  @Test
  void shouldReturn401_whenNotAuthenticated() throws Exception {
    mockMvc.perform(get("/movies")).andExpect(status().isUnauthorized());
  }

  private String movieBody() {
    return "{\"title\":\"Inception\",\"genres\":[\"ACTION\",\"SCI_FI\"]}";
  }
}
