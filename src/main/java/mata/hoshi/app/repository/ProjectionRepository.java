package mata.hoshi.app.repository;

import java.util.UUID;
import mata.hoshi.app.repository.model.JProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectionRepository extends JpaRepository<JProjection, UUID> {}
