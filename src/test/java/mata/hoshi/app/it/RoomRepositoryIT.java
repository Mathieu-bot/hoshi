package mata.hoshi.app.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.UUID;
import mata.hoshi.app.conf.FacadeIT;
import mata.hoshi.app.repository.RoomRepository;
import mata.hoshi.app.repository.model.JRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RoomRepositoryIT extends FacadeIT {

  @Autowired private RoomRepository roomRepository;

  @Test
  void save_and_find_room() {
    var room = JRoom.builder().number("SALLE-1").capacity(120).build();

    var saved = roomRepository.save(room);

    Optional<JRoom> found = roomRepository.findById(saved.getId());
    assertTrue(found.isPresent());
    assertEquals("SALLE-1", found.get().getNumber());
    assertEquals(120, found.get().getCapacity());
  }

  @Test
  void new_room_has_generated_id() {
    var saved = roomRepository.save(JRoom.builder().number("SALLE-2").capacity(50).build());

    assertTrue(saved.getId() instanceof UUID);
  }
}
