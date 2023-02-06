package rc.holding.houseplants.domain.hateoas.api;

import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.domain.hateoas.impl.HalModel;
@Relation(value = "user", collectionRelation = "users")
public class UserModel extends HalModel<UserModel> {
    @Getter private final String firstName;
    @Getter private final String lastName;
    @Getter private final String email;
    @Getter private final String username;

    public UserModel(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
