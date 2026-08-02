package mata.hoshi.app.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mata.hoshi.app.model.ReservationStatus;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JReservation {

  @Id @GeneratedValue private UUID id;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ReservationStatus status;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private JUser user;

  @ManyToOne
  @JoinColumn(name = "projection_id", nullable = false)
  private JProjection projection;

  @ManyToMany
  @JoinTable(
      name = "reservation_seat",
      joinColumns = @JoinColumn(name = "reservation_id"),
      inverseJoinColumns = @JoinColumn(name = "seat_id"))
  private Set<JSeat> seats;
}
