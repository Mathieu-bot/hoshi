package mata.hoshi.app.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import java.util.UUID;
import mata.hoshi.app.model.Genre;
import mata.hoshi.app.model.Movie;
import mata.hoshi.app.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;

class MovieServiceImplTest {

  private final MovieServiceImpl movieService = new MovieServiceImpl();

  @Test
  void updateThrowsNotImplemented() {
    Movie movie =
        Movie.builder()
            .id(UUID.randomUUID())
            .title("Inception")
            .genres(Set.of(Genre.ACTION))
            .build();

    assertThrows(UnsupportedOperationException.class, () -> movieService.update(movie));
  }

  @Test
  void findByIdThrowsNotImplemented() {
    assertThrows(
        UnsupportedOperationException.class, () -> movieService.findById(UUID.randomUUID()));
  }
}
