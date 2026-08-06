package mata.hoshi.app.service;

import java.util.List;
import java.util.UUID;
import mata.hoshi.app.dto.reservation.request.ReservationRequest;
import mata.hoshi.app.model.Reservation;

public interface ReservationService {

  List<Reservation> findAll();

  Reservation findById(UUID id);

  Reservation update(UUID id, ReservationRequest request);
}
