package mata.hoshi.app.repository;

import java.util.List;
import mata.hoshi.app.PojaGenerated;
import mata.hoshi.app.repository.model.Dummy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@PojaGenerated
@Repository
public interface DummyRepository extends JpaRepository<Dummy, String> {

  @Override
  List<Dummy> findAll();
}
