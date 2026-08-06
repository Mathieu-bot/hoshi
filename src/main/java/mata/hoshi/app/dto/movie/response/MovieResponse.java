package mata.hoshi.app.dto.movie.response;

import java.util.Set;
import java.util.UUID;
import lombok.Data;
import mata.hoshi.app.model.Genre;

@Data
public class MovieResponse {

    private UUID id;

    private String title;

    private Set<Genre> genres;
}