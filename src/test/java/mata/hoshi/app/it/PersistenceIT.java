package mata.hoshi.app.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mata.hoshi.app.conf.FacadeIT;
import mata.hoshi.app.model.Genre;
import mata.hoshi.app.model.Movie;
import mata.hoshi.app.model.Projection;
import mata.hoshi.app.model.Reservation;
import mata.hoshi.app.model.ReservationStatus;
import mata.hoshi.app.model.Room;
import mata.hoshi.app.model.Seat;
import mata.hoshi.app.model.User;
import mata.hoshi.app.model.UserRole;
import mata.hoshi.app.repository.MovieRepository;
import mata.hoshi.app.repository.ProjectionRepository;
import mata.hoshi.app.repository.ReservationRepository;
import mata.hoshi.app.repository.RoomRepository;
import mata.hoshi.app.repository.SeatRepository;
import mata.hoshi.app.repository.UserRepository;
import mata.hoshi.app.repository.model.JMovie;
import mata.hoshi.app.repository.model.JProjection;
import mata.hoshi.app.repository.model.JReservation;
import mata.hoshi.app.repository.model.JRoom;
import mata.hoshi.app.repository.model.JSeat;
import mata.hoshi.app.repository.model.JUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class PersistenceIT extends FacadeIT {

  @Autowired RoomRepository rooms;
  @Autowired SeatRepository seats;
  @Autowired UserRepository users;
  @Autowired MovieRepository movies;
  @Autowired ProjectionRepository projections;
  @Autowired ReservationRepository reservations;

  @Test
  void cinema_graph_is_persisted_and_readable() {
    var movie = movies.save(JMovie.builder().title("Dune").genres(Set.of(Genre.SCI_FI)).build());
    var room = rooms.save(JRoom.builder().number("S1").capacity(100).build());
    var seat = seats.save(JSeat.builder().number("A1").room(room).build());
    var user =
        users.save(
            JUser.builder()
                .firstName("Jean")
                .lastName("Dupont")
                .email("j@d.mg")
                .password("x")
                .role(UserRole.CLIENT)
                .build());
    var projection =
        projections.save(
            JProjection.builder()
                .datetime(Instant.parse("2026-08-10T20:00:00Z"))
                .seatPrice(BigDecimal.TEN)
                .movie(movie)
                .room(room)
                .build());
    reservations.save(
        JReservation.builder()
            .status(ReservationStatus.PENDING)
            .user(user)
            .projection(projection)
            .seats(Set.of(seat))
            .build());
    reservations.flush();

    var res = reservations.findAll().stream().findAny().orElseThrow();
    assertEquals(1, res.getSeats().size());
    assertEquals(Set.of(Genre.SCI_FI), movie.getGenres());
    assertNotNull(res.getCreatedAt());
  }

  @Test
  void domain_models_expose_their_fields() {
    var room = Room.builder().id(UUID.randomUUID()).number("S2").capacity(50).build();
    var seat = Seat.builder().id(UUID.randomUUID()).number("B1").roomId(room.id()).build();
    var movie =
        Movie.builder()
            .id(UUID.randomUUID())
            .title("Le Comte")
            .genres(Set.of(Genre.COMEDY))
            .build();
    var projection =
        Projection.builder()
            .id(UUID.randomUUID())
            .datetime(Instant.parse("2026-08-11T18:00:00Z"))
            .seatPrice(BigDecimal.TEN)
            .movieId(movie.id())
            .roomId(room.id())
            .build();
    var reservation =
        Reservation.builder()
            .id(UUID.randomUUID())
            .createdAt(Instant.parse("2026-08-01T10:00:00Z"))
            .status(ReservationStatus.SUCCESS)
            .userId(UUID.randomUUID())
            .projectionId(projection.id())
            .seatIds(Set.of(seat.id()))
            .build();
    var user =
        User.builder().id(UUID.randomUUID()).firstName("Marie").role(UserRole.MANAGER).build();

    var all = List.of(room, seat, movie, projection, reservation, user);
    assertTrue(all.toString().contains("Marie"));
  }
}
