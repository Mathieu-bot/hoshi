package mata.hoshi.app.service.impl;

import java.util.List;
import java.util.UUID;
import mata.hoshi.app.dto.reservation.request.ReservationRequest;
import mata.hoshi.app.model.Reservation;
import mata.hoshi.app.service.ReservationService;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

  @Override
  public List<Reservation> findAll() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Reservation findById(UUID id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Reservation update(UUID id, ReservationRequest request) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
