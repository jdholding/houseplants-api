package rc.holding.houseplants.repository.api;

import java.util.List;


import org.springframework.stereotype.Component;
import rc.holding.houseplants.domain.Plant;

public interface PlantRepository {
    List<Plant> findAllPlants();
}
