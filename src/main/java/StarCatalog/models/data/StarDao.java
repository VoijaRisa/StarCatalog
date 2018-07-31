package StarCatalog.models.data;

import StarCatalog.models.Star;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface StarDao extends CrudRepository<Star, Integer> {
}
