package StarCatalog.models.data;

import StarCatalog.models.Observation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ObservationDao extends CrudRepository<Observation, Integer> {
}
