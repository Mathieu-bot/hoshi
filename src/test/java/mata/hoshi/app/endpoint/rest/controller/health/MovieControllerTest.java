package mata.hoshi.app.endpoint.rest.controller.health;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import java.util.UUID;
import mata.hoshi.app.dto.movie.request.MovieRequest;
import mata.hoshi.app.dto.movie.response.MovieResponse;
import mata.hoshi.app.mapper.MovieMapper;
import mata.hoshi.app.model.Genre;
import mata.hoshi.app.model.Movie;
import mata.hoshi.app.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MovieController.class)
@AutoConfigureMockMvc(addFilters = false)
class MovieControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private MovieService movieService;

  @MockBean private MovieMapper movieMapper;

  @Test
  void shouldUpdateMovie() throws Exception {
    UUID id = UUID.randomUUID();

    MovieRequest request = new MovieRequest();
    request.setTitle("Inception");
    request.setGenres(Set.of(Genre.ACTION, Genre.SCI_FI));

    Movie movie = movieMapper.toModel(request);

    Movie updated =
        Movie.builder().id(id).title(request.getTitle()).genres(request.getGenres()).build();

    MovieResponse response = new MovieResponse();
    response.setId(id);
    response.setTitle(request.getTitle());
    response.setGenres(request.getGenres());

    when(movieMapper.toModel(request)).thenReturn(movie);
    when(movieService.update(movie)).thenReturn(updated);
    when(movieMapper.toResponse(updated)).thenReturn(response);

    mockMvc
        .perform(
            put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()))
        .andExpect(jsonPath("$.title").value("Inception"))
        .andExpect(jsonPath("$.genres", containsInAnyOrder("ACTION", "SCI_FI")));
  }
}
