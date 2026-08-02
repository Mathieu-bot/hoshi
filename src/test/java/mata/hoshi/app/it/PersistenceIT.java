package mata.hoshi.app.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
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

  @Autowired private RoomRepository roomRepository;
  @Autowired private SeatRepository seatRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private MovieRepository movieRepository;
  @Autowired private ProjectionRepository projectionRepository;
  @Autowired private ReservationRepository reservationRepository;

  @Test
  void persist_and_read_full_cinema_graph() {
    var room = roomRepository.save(JRoom.builder().number("SALLE-1").capacity(100).build());
    var seatA = seatRepository.save(JSeat.builder().number("A1").room(room).build());
    var seatB = seatRepository.save(JSeat.builder().number("A2").room(room).build());

    var user =
        userRepository.save(
            JUser.builder()
                .firstName("Jean")
                .lastName("Dupont")
                .birthdate(LocalDate.of(1990, 5, 1))
                .email("jean.dupont@mail.com")
                .password("secret")
                .phone("+261340000000")
                .role(UserRole.CLIENT)
                .build());

    var movie =
        movieRepository.save(
            JMovie.builder().title("Dune").genres(Set.of(Genre.SCI_FI, Genre.ACTION)).build());

    var projection =
        projectionRepository.save(
            JProjection.builder()
                .datetime(Instant.parse("2026-08-10T20:00:00Z"))
                .seatPrice(new BigDecimal("15000.00"))
                .movie(movie)
                .room(room)
                .build());

    var reservation =
        reservationRepository.save(
            JReservation.builder()
                .status(ReservationStatus.PENDING)
                .user(user)
                .projection(projection)
                .seats(Set.of(seatA, seatB))
                .build());
    reservationRepository.flush();

    var loadedRoom = roomRepository.findById(room.getId()).orElseThrow();
    assertEquals(room.getId(), loadedRoom.getId());
    assertEquals("SALLE-1", loadedRoom.getNumber());
    assertEquals(100, loadedRoom.getCapacity());

    var loadedSeatA = seatRepository.findById(seatA.getId()).orElseThrow();
    assertEquals("A1", loadedSeatA.getNumber());
    assertEquals(room.getId(), loadedSeatA.getRoom().getId());

    var loadedUser = userRepository.findById(user.getId()).orElseThrow();
    assertEquals("Jean", loadedUser.getFirstName());
    assertEquals("Dupont", loadedUser.getLastName());
    assertEquals(LocalDate.of(1990, 5, 1), loadedUser.getBirthdate());
    assertEquals("jean.dupont@mail.com", loadedUser.getEmail());
    assertEquals("secret", loadedUser.getPassword());
    assertEquals("+261340000000", loadedUser.getPhone());
    assertEquals(UserRole.CLIENT, loadedUser.getRole());

    var loadedMovie = movieRepository.findById(movie.getId()).orElseThrow();
    assertEquals("Dune", loadedMovie.getTitle());
    assertEquals(Set.of(Genre.SCI_FI, Genre.ACTION), loadedMovie.getGenres());

    var loadedProjection = projectionRepository.findById(projection.getId()).orElseThrow();
    assertEquals(Instant.parse("2026-08-10T20:00:00Z"), loadedProjection.getDatetime());
    assertEquals(0, loadedProjection.getSeatPrice().compareTo(new BigDecimal("15000.00")));
    assertEquals(movie.getId(), loadedProjection.getMovie().getId());
    assertEquals(room.getId(), loadedProjection.getRoom().getId());

    var loadedReservation = reservationRepository.findById(reservation.getId()).orElseThrow();
    assertEquals(ReservationStatus.PENDING, loadedReservation.getStatus());
    assertEquals(user.getId(), loadedReservation.getUser().getId());
    assertEquals(projection.getId(), loadedReservation.getProjection().getId());
    assertEquals(
        Set.of(seatA.getId(), seatB.getId()),
        loadedReservation.getSeats().stream().map(JSeat::getId).collect(Collectors.toSet()));
    assertNotNull(loadedReservation.getCreatedAt());

    loadedRoom.setCapacity(120);
    roomRepository.save(loadedRoom);
    assertEquals(120, roomRepository.findById(room.getId()).orElseThrow().getCapacity());
  }

  @Test
  void domain_models_expose_their_fields() {
    var room = Room.builder().id(UUID.randomUUID()).number("SALLE-2").capacity(50).build();
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
        User.builder()
            .id(UUID.randomUUID())
            .firstName("Marie")
            .lastName("Martin")
            .birthdate(LocalDate.of(2000, 1, 1))
            .email("marie.martin@mail.com")
            .password("secret")
            .phone("+261330000000")
            .role(UserRole.MANAGER)
            .build();

    assertEquals("SALLE-2", room.number());
    assertEquals(50, room.capacity());
    assertEquals("B1", seat.number());
    assertEquals(room.id(), seat.roomId());
    assertEquals("Le Comte", movie.title());
    assertTrue(movie.genres().contains(Genre.COMEDY));
    assertEquals(Instant.parse("2026-08-11T18:00:00Z"), projection.datetime());
    assertEquals(BigDecimal.TEN, projection.seatPrice());
    assertEquals(movie.id(), projection.movieId());
    assertEquals(room.id(), projection.roomId());
    assertEquals(ReservationStatus.SUCCESS, reservation.status());
    assertEquals(1, reservation.seatIds().size());
    assertEquals(Instant.parse("2026-08-01T10:00:00Z"), reservation.createdAt());
    assertEquals("Marie", user.firstName());
    assertEquals("Martin", user.lastName());
    assertEquals(LocalDate.of(2000, 1, 1), user.birthdate());
    assertEquals(UserRole.MANAGER, user.role());
  }
}
