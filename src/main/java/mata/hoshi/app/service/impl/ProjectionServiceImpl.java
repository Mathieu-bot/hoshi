package mata.hoshi.app.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mata.hoshi.app.dto.projection.request.ProjectionRequest;
import mata.hoshi.app.model.Projection;
import mata.hoshi.app.service.ProjectionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectionServiceImpl implements ProjectionService {

  @Override
  public List<Projection> findAll() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Projection update(UUID id, ProjectionRequest request) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
