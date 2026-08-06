package mata.hoshi.app.mapper;

import mata.hoshi.app.dto.movie.request.MovieRequest;
import mata.hoshi.app.dto.movie.response.MovieResponse;
import mata.hoshi.app.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

  MovieResponse toResponse(Movie movie);

  @Mapping(target = "id", ignore = true)
  Movie toModel(MovieRequest request);
}
