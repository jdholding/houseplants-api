package rc.holding.houseplants.repository.api;

import java.util.List;
import java.util.Optional;

import rc.holding.houseplants.domain.Plant;

public interface PlantRepository {
    List<Plant> findAllPlants();

    Optional<Plant> findById(Integer id);
    
    Plant insert(Plant plant); 

    Plant update(Plant plant); 
}
