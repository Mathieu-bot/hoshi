package mata.hoshi.app.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JRoom {

  @Id @GeneratedValue private UUID id;

  @NotBlank
  @Column(nullable = false, unique = true, length = 50)
  private String number;

  @Column(nullable = false)
  private int capacity;
}
