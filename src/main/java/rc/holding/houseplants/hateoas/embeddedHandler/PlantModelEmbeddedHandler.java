package rc.holding.houseplants.hateoas.embeddedHandler;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.domain.hateoas.api.PlantModel;
import rc.holding.houseplants.domain.search.CommentParams;
import rc.holding.houseplants.domain.search.PhotoParams;
import rc.holding.houseplants.hateoas.assembler.CommentModelAssembler;
import rc.holding.houseplants.hateoas.assembler.PhotoModelAssembler;
import rc.holding.houseplants.hateoas.assembler.PlantModelAssembler;
import rc.holding.houseplants.repository.api.CommentRepository;
import rc.holding.houseplants.repository.api.PhotoRepository;

@Component
@AllArgsConstructor
public class PlantModelEmbeddedHandler implements EmbeddedHandler<Plant, PlantModel> {

  private final PhotoRepository photoRepo;
  private final CommentRepository commentRepo;

  public enum Embedded implements Embeddable<PlantModel> {
    PHOTOS,
    COMMENTS
  }

  @Override
  public RepresentationModelAssembler<Plant, PlantModel> instanciateAssembler() {
    return new PlantModelAssembler();
  }

  @Override
  public PlantModel addEmbeddeds(
      Plant entity, PlantModel model, Embeddable<PlantModel>[] embeddeds) {
    for (Embedded embedded : (Embedded[]) embeddeds) {
      switch (embedded) {
        case PHOTOS:
          var photoParams = PhotoParams.builder().plantId(entity.getId()).size(10).build();
          var photos = photoRepo.findAllByParams(photoParams);
          model.embed("photos", new PhotoModelAssembler().toCollectionModel(photos));
        case COMMENTS:
          var commentParams = CommentParams.builder().plantId(entity.getId()).size(10).build();
          var comments = commentRepo.findAllByParams(commentParams);
          model.embed("comments", new CommentModelAssembler().toCollectionModel(comments));
      }
    }
    return model;
  }
}
