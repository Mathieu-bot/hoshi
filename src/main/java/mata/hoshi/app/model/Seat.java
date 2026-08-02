package mata.hoshi.app.model;

import java.util.UUID;
import lombok.Builder;

@Builder
public record Seat(UUID id, String number, UUID roomId) {}
