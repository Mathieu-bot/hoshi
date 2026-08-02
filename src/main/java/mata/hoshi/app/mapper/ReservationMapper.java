package mata.hoshi.app.mapper;

import java.util.List;
import java.util.UUID;
import mata.hoshi.app.dto.reservation.request.ReservationRequest;
import mata.hoshi.app.dto.reservation.response.ReservationResponse;
import mata.hoshi.app.model.Reservation;
import mata.hoshi.app.model.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "projection.id", target = "projectionId")
  @Mapping(source = "seats", target = "seatIds")
  ReservationResponse toResponse(Reservation reservation);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "projection", ignore = true)
  @Mapping(source = "seatIds", target = "seats")
  Reservation toEntity(ReservationRequest request);

  default List<UUID> mapSeatsToIds(List<Seat> seats) {
    if (seats == null) return List.of();
    return seats.stream().map(Seat::getId).toList();
  }

  default List<Seat> mapIdsToSeats(List<UUID> ids) {
    if (ids == null) return List.of();
    return ids.stream()
        .map(
            id -> {
              Seat seat = new Seat();
              seat.setId(id);
              return seat;
            })
        .toList();
  }
}
