package mata.hoshi.app.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import mata.hoshi.app.dto.reservation.request.ReservationRequest;
import org.junit.jupiter.api.Test;

class ReservationServiceImplTest {

  private final ReservationServiceImpl service = new ReservationServiceImpl();

  @Test
  void findAllIsNotImplementedYet() {
    assertThrows(UnsupportedOperationException.class, service::findAll);
  }

  @Test
  void findByIdIsNotImplementedYet() {
    assertThrows(UnsupportedOperationException.class, () -> service.findById(UUID.randomUUID()));
  }

  @Test
  void updateIsNotImplementedYet() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> service.update(UUID.randomUUID(), new ReservationRequest()));
  }
}
