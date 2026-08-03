package mata.hoshi.app.endpoint.rest.controller.health.;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mata.hoshi.app.dto.reservation.request.ReservationRequest;
import mata.hoshi.app.dto.reservation.response.ReservationResponse;
import mata.hoshi.app.mapper.ReservationMapper;
import mata.hoshi.app.model.Reservation;
import mata.hoshi.app.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;
  private final ReservationMapper reservationMapper;


  @GetMapping
  public ResponseEntity<List<ReservationResponse>> getAllReservations() {

    List<Reservation> reservations = reservationService.findAll();

    return ResponseEntity.ok(
            reservations.stream()
                    .map(reservationMapper::toResponse)
                    .toList()
    );
  }


  @GetMapping("/{id}")
  public ResponseEntity<ReservationResponse> getById(@PathVariable UUID id) {

    Reservation reservation = reservationService.findById(id);

    return ResponseEntity.ok(reservationMapper.toResponse(reservation));
  }


  @PutMapping("/{id}")
  public ResponseEntity<ReservationResponse> update(
          @PathVariable UUID id,
          @Valid @RequestBody ReservationRequest request) {

    Reservation updated = reservationService.update(id, request);

    return ResponseEntity.ok(reservationMapper.toResponse(updated));
  }

}
