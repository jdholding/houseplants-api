package rc.holding.houseplants.hateoas.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import rc.holding.houseplants.contoller.UserController;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.domain.hateoas.api.UserModel;

public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

  public UserModelAssembler() {
    super(UserController.class, UserModel.class);
  }

  @Override
  protected UserModel instantiateModel(User user) {
    return new UserModel(user);
  }

  @Override
  public UserModel toModel(User user) {
    return createModelWithId(user.getId(), user);
  }
}
