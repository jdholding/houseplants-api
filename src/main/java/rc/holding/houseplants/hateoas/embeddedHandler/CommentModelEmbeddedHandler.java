package rc.holding.houseplants.hateoas.embeddedHandler;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import rc.holding.houseplants.domain.Comment;
import rc.holding.houseplants.domain.hateoas.api.CommentModel;
import rc.holding.houseplants.exception.ResourceNotFoundException;
import rc.holding.houseplants.hateoas.assembler.CommentModelAssembler;
import rc.holding.houseplants.hateoas.assembler.PlantModelAssembler;
import rc.holding.houseplants.hateoas.assembler.UserModelAssembler;
import rc.holding.houseplants.repository.api.PlantRepository;
import rc.holding.houseplants.repository.api.UserRepository;

@Component
@AllArgsConstructor
public class CommentModelEmbeddedHandler implements EmbeddedHandler<Comment, CommentModel> {

  private final UserRepository userRepo;
  private final PlantRepository plantRepo;

  public enum Embedded implements Embeddable<CommentModel> {
    USER,
    PLANT
  }

  @Override
  public RepresentationModelAssembler<Comment, CommentModel> instanciateAssembler() {
    return new CommentModelAssembler();
  }

  @Override
  public CommentModel addEmbeddeds(
      Comment entity, CommentModel model, Embeddable<CommentModel>[] embeddeds) {
    for (CommentModelEmbeddedHandler.Embedded embedded :
        (CommentModelEmbeddedHandler.Embedded[]) embeddeds) {
      switch (embedded) {
        case USER:
          var user =
              userRepo
                  .findById(entity.getUserId())
                  .orElseThrow(() -> new ResourceNotFoundException(entity.getUserId()));
          model.embed("user", new UserModelAssembler().toModel(user));
        case PLANT:
          var plant =
              plantRepo
                  .findById(entity.getPlantId())
                  .orElseThrow(() -> new ResourceNotFoundException(entity.getPlantId()));
          model.embed("plant", new PlantModelAssembler().toModel(plant));
      }
    }
    return model;
  }
}
