package rc.holding.houseplants.contoller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.repository.api.PlantRepository;

@RestController
@RequestMapping("/plants")
@AllArgsConstructor
public class PlantController {
    
    private final PlantRepository repo; 

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Plant> getPlants() {
        return repo.findAllPlants(); 
    }
}
