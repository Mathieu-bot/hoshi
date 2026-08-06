package mata.hoshi.app.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import mata.hoshi.app.dto.projection.request.ProjectionRequest;
import mata.hoshi.app.service.impl.ProjectionServiceImpl;
import org.junit.jupiter.api.Test;

class ProjectionServiceImplTest {

  private final ProjectionServiceImpl projectionService = new ProjectionServiceImpl();

  @Test
  void findAllThrowsNotImplemented() {
    assertThrows(UnsupportedOperationException.class, projectionService::findAll);
  }

  @Test
  void updateThrowsNotImplemented() {
    ProjectionRequest request = new ProjectionRequest();
    assertThrows(
        UnsupportedOperationException.class,
        () -> projectionService.update(UUID.randomUUID(), request));
  }
}
