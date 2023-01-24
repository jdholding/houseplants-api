package rc.holding.houseplants.contoller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.exception.ResourceNotFoundException;
import rc.holding.houseplants.repository.api.PlantRepository;

import java.util.List;

@RestController
@RequestMapping("/plants")
@AllArgsConstructor
public class PlantController {

    private final PlantRepository repo;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<Plant> getPlants() {
        var plants = repo.findAllPlants();
        return CollectionModel.of(plants); 
    }

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Plant> getPlantById(@PathVariable("id") Integer id) {
        var plant = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return EntityModel.of(plant);   
    }

    @PostMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Plant> addPlant(@RequestBody Plant plant) {
        var createdPlant = repo.insert(plant);
        return EntityModel.of(createdPlant);  
    }

}
