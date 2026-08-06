package mata.hoshi.app.endpoint.rest.controller.health;


import lombok.RequiredArgsConstructor;
import mata.hoshi.app.dto.movie.request.MovieRequest;
import mata.hoshi.app.dto.movie.response.MovieResponse;
import mata.hoshi.app.mapper.MovieMapper;
import mata.hoshi.app.model.Movie;
import mata.hoshi.app.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @PutMapping
    public ResponseEntity<MovieResponse> update(@RequestBody MovieRequest request) {

        Movie movie = movieMapper.toModel(request);

        Movie updated = movieService.update(movie);

        return ResponseEntity.ok(movieMapper.toResponse(updated));
    }
}

