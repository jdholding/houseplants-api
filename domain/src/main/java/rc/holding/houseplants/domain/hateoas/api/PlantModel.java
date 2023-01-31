package rc.holding.houseplants.domain.hateoas.api;

import java.time.LocalDateTime;

import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.domain.hateoas.impl.HalModel;

@Relation(value= "plant", collectionRelation= "plants")
public class PlantModel extends HalModel<PlantModel>{
    @Getter private final Integer parentId;
    @Getter private final Integer trefleId;
    @Getter private final String family;
    @Getter private final String genus; 
    @Getter private final String species;
    @Getter private final String commonName;
    @Getter private final LocalDateTime dateCreated;
    
    public PlantModel(Plant plant) {
        this.parentId = plant.getParentId(); 
        this.trefleId = plant.getTrefleId(); 
        this.family = plant.getFamily(); 
        this.genus = plant.getGenus(); 
        this.species = plant.getSpecies();
        this.commonName = plant.getCommonName(); 
        this.dateCreated = plant.getDateCreated().toLocalDateTime(); 
    }
    
}
