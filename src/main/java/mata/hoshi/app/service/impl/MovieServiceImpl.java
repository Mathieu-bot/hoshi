package mata.hoshi.app.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mata.hoshi.app.model.Movie;
import mata.hoshi.app.service.MovieService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    @Override
    public Movie update(Movie movie) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Movie findById(UUID id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}