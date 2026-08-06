package mata.hoshi.app.service;

import java.util.List;
import java.util.UUID;
import mata.hoshi.app.dto.projection.request.ProjectionRequest;
import mata.hoshi.app.model.Projection;

public interface ProjectionService {

  List<Projection> findAll();

  Projection update(UUID id, ProjectionRequest request);
}
