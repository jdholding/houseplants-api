package rc.holding.houseplants.repository.api;

import java.util.List;


import rc.holding.houseplants.domain.Plant;


public interface PlantRepository {
    List<Plant> findAllPlants();
}
