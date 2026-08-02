package mata.hoshi.app.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seat", uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "number"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JSeat {

  @Id @GeneratedValue private UUID id;

  @NotBlank
  @Column(nullable = false, length = 10)
  private String number;

  @ManyToOne
  @JoinColumn(name = "room_id", nullable = false)
  private JRoom room;
}
