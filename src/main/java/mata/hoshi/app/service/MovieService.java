package mata.hoshi.app.service;

import java.util.UUID;
import mata.hoshi.app.model.Movie;

public interface MovieService {

  Movie update(Movie movie);

  Movie findById(UUID id);
}
