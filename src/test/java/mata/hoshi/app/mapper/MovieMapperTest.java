package mata.hoshi.app.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;
import java.util.UUID;
import mata.hoshi.app.dto.movie.request.MovieRequest;
import mata.hoshi.app.dto.movie.response.MovieResponse;
import mata.hoshi.app.model.Genre;
import mata.hoshi.app.model.Movie;
import org.junit.jupiter.api.Test;

class MovieMapperTest {

  private final MovieMapper mapper = new MovieMapperImpl();

  @Test
  void toResponseMapsAllFields() {
    UUID id = UUID.randomUUID();

    Movie movie =
        Movie.builder()
            .id(id)
            .title("Inception")
            .genres(Set.of(Genre.ACTION, Genre.SCI_FI))
            .build();

    MovieResponse response = mapper.toResponse(movie);

    assertEquals(movie.id(), response.getId());
    assertEquals(movie.title(), response.getTitle());
    assertEquals(movie.genres(), response.getGenres());
  }

  @Test
  void toModelMapsRequestAndIgnoresId() {
    MovieRequest request = new MovieRequest();
    request.setTitle("Inception");
    request.setGenres(Set.of(Genre.ACTION, Genre.SCI_FI));

    Movie movie = mapper.toModel(request);

    assertNull(movie.id());
    assertEquals(request.getTitle(), movie.title());
    assertEquals(request.getGenres(), movie.genres());
  }
}
