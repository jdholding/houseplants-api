package rc.holding.houseplants.hateoas.embeddedHandler;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.domain.hateoas.api.PhotoModel;
import rc.holding.houseplants.exception.ResourceNotFoundException;
import rc.holding.houseplants.hateoas.assembler.PhotoModelAssembler;
import rc.holding.houseplants.repository.api.PlantRepository;

@Component
@AllArgsConstructor
public class PhotoModelEmbeddedHandler implements EmbeddedHandler<Photo, PhotoModel> {
    private final PlantRepository plantRepo;
    public enum Embedded implements Embeddable<PhotoModel> {
        PLANT
    }

    @Override
    public RepresentationModelAssembler<Photo, PhotoModel> instanciateAssembler() { return new PhotoModelAssembler(); }

    @Override
    public PhotoModel addEmbeddeds(Photo entity, PhotoModel model, Embeddable<PhotoModel>[] embeddeds) {
        for (Embedded embedded : (Embedded[]) embeddeds) {
            switch (embedded) {
                case PLANT:
                    var plant = plantRepo.findById(entity.getPlantId()).orElseThrow(() -> new ResourceNotFoundException(entity.getPlantId()));
                    model.embed("plant", plant);
            }
        }
        return model;
    }
}
