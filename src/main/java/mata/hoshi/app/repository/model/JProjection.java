package mata.hoshi.app.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projection")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JProjection {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private Instant datetime;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal seatPrice;

  @ManyToOne
  @JoinColumn(name = "movie_id", nullable = false)
  private JMovie movie;

  @ManyToOne
  @JoinColumn(name = "room_id", nullable = false)
  private JRoom room;
}
