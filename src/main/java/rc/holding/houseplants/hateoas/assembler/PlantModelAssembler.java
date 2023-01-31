package rc.holding.houseplants.hateoas.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import rc.holding.houseplants.contoller.PlantController;
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.domain.hateoas.api.PlantModel;

public class PlantModelAssembler extends RepresentationModelAssemblerSupport<Plant, PlantModel>{
    
    public PlantModelAssembler() { super(PlantController.class, PlantModel.class); }

    @Override
    protected PlantModel instantiateModel(Plant plant) { return new PlantModel(plant); }

    @Override
    public PlantModel toModel(Plant plant) {
        return createModelWithId(plant.getId(), plant);
    }
}
