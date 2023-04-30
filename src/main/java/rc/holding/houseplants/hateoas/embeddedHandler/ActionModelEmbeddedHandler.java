package rc.holding.houseplants.hateoas.embeddedHandler;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.hateoas.api.ActionModel;
import rc.holding.houseplants.exception.ResourceNotFoundException;
import rc.holding.houseplants.hateoas.assembler.ActionModelAssembler;
import rc.holding.houseplants.hateoas.assembler.PlantModelAssembler;
import rc.holding.houseplants.hateoas.assembler.UserModelAssembler;
import rc.holding.houseplants.repository.api.PlantRepository;
import rc.holding.houseplants.repository.api.UserRepository;

@Component
@AllArgsConstructor
public class ActionModelEmbeddedHandler implements EmbeddedHandler<Action, ActionModel> {

  private final UserRepository userRepo;
  private final PlantRepository plantRepo;

  public enum Embedded implements Embeddable<ActionModel> {
    USER,
    PLANT
  }

  @Override
  public RepresentationModelAssembler<Action, ActionModel> instanciateAssembler() {
    return new ActionModelAssembler();
  }

  @Override
  public ActionModel addEmbeddeds(
      Action entity, ActionModel model, Embeddable<ActionModel>[] embeddeds) {
    for (ActionModelEmbeddedHandler.Embedded embedded :
        (ActionModelEmbeddedHandler.Embedded[]) embeddeds) {
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
