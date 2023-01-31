package rc.holding.houseplants.hateoas.embeddedHandler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.domain.hateoas.api.PlantModel;
import rc.holding.houseplants.hateoas.assembler.PlantModelAssembler;
import rc.holding.houseplants.repository.api.PhotoRepository;

@Component
@AllArgsConstructor
public class PlantModelEmbeddedHandler implements EmbeddedHandler<Plant, PlantModel> {

    // private final PhotoRepository photoRepo; 
    
    public enum Embedded implements Embeddable<PlantModel> {
        PHOTOS,
        COMMENTS
    }

    @Override
    public RepresentationModelAssembler<Plant, PlantModel> instanciateAssembler() {
        return new PlantModelAssembler(); 
    }

    @Override
    public PlantModel addEmbeddeds(Plant entity, PlantModel model, Embeddable<PlantModel>[] embeddeds) {
        for (Embedded embedded : (Embedded[]) embeddeds) {
            switch (embedded) {
                case PHOTOS:
                    // TODO embed photos
                    // var photos = photoRepo.getPhotosByPlantId
                    // maybe only the most recent 10 or so
                    // model.embed("photos", photos)
                case COMMENTS:
                    // TODO embed comments    
            }
        }
        return model; 
    }
}
