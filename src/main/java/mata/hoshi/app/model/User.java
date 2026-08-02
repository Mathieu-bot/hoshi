package mata.hoshi.app.model;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;

@Builder
public record User(
    UUID id,
    String firstName,
    String lastName,
    LocalDate birthdate,
    String email,
    String password,
    String phone,
    UserRole role) {}
