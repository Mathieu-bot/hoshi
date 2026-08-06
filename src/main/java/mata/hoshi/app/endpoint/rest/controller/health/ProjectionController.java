package mata.hoshi.app.endpoint.rest.controller.health;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mata.hoshi.app.dto.projection.request.ProjectionRequest;
import mata.hoshi.app.dto.projection.response.ProjectionResponse;
import mata.hoshi.app.mapper.ProjectionMapper;
import mata.hoshi.app.model.Projection;
import mata.hoshi.app.service.ProjectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projections")
@RequiredArgsConstructor
public class ProjectionController {

  private final ProjectionService projectionService;
  private final ProjectionMapper projectionMapper;

  @GetMapping
  public ResponseEntity<List<ProjectionResponse>> getAll() {

    List<Projection> projections = projectionService.findAll();

    return ResponseEntity.ok(projections.stream().map(projectionMapper::toResponse).toList());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProjectionResponse> update(
      @PathVariable UUID id, @RequestBody ProjectionRequest request) {

    Projection updated = projectionService.update(id, request);

    return ResponseEntity.ok(projectionMapper.toResponse(updated));
  }
}
