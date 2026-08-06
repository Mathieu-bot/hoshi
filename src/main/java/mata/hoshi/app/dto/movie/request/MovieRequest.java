package mata.hoshi.app.dto.movie.request;

import java.util.Set;
import lombok.Data;
import mata.hoshi.app.model.Genre;

@Data
public class MovieRequest {

  private String title;

  private Set<Genre> genres;
}
