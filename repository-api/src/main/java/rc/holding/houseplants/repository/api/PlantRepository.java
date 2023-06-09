package rc.holding.houseplants.repository.api;

import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.repository.tools.PagedReadRepository;

public interface PlantRepository extends PagedReadRepository<Plant, Integer> {

  Integer insert(Plant plant);

  Plant update(Plant plant);
}
