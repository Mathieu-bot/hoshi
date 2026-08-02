package mata.hoshi.app.model;

import java.util.Set;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Movie(UUID id, String title, Set<Genre> genres) {}
