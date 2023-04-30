package rc.holding.houseplants.hateoas.embeddedHandler;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.domain.hateoas.api.UserModel;
import rc.holding.houseplants.domain.search.CommentParams;
import rc.holding.houseplants.domain.search.PlantParams;
import rc.holding.houseplants.hateoas.assembler.CommentModelAssembler;
import rc.holding.houseplants.hateoas.assembler.PlantModelAssembler;
import rc.holding.houseplants.hateoas.assembler.UserModelAssembler;
import rc.holding.houseplants.repository.api.CommentRepository;
import rc.holding.houseplants.repository.api.PlantRepository;

@Component
@AllArgsConstructor
public class UserModelEmbeddedHandler implements EmbeddedHandler<User, UserModel> {

  private final PlantRepository plantRepo;
  private final CommentRepository commentRepo;

  public enum Embedded implements Embeddable<UserModel> {
    PLANTS,
    COMMENTS,
  }

  @Override
  public RepresentationModelAssembler<User, UserModel> instanciateAssembler() {
    return new UserModelAssembler();
  }

  @Override
  public UserModel addEmbeddeds(User entity, UserModel model, Embeddable<UserModel>[] embeddeds) {
    for (UserModelEmbeddedHandler.Embedded embedded :
        (UserModelEmbeddedHandler.Embedded[]) embeddeds) {
      switch (embedded) {
        case PLANTS:
          var plantParams = PlantParams.builder().userId(entity.getId()).size(10).build();
          var plants = plantRepo.findAllByParams(plantParams);
          model.embed("plants", new PlantModelAssembler().toCollectionModel(plants));
        case COMMENTS:
          var commentParams = CommentParams.builder().userId(entity.getId()).size(10).build();
          var comments = commentRepo.findAllByParams(commentParams);
          model.embed("comments", new CommentModelAssembler().toCollectionModel(comments));
      }
    }
    return model;
  }
}
