package rc.holding.houseplants.repository.api;

import java.util.List;
import java.util.Optional;
import rc.holding.houseplants.domain.User;

public interface UserRepository {
  List<User> findAll();

  Optional<User> findById(Integer id);

  User insert(User user);

  User update(User user);

  void delete(Integer id);
}
