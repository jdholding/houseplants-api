package rc.holding.houseplants.repository.api;

import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.repository.tools.PagedReadRepository;

public interface UserRepository extends PagedReadRepository<User, Integer> {
  Integer insert(User user);

  User update(User user);

  void delete(Integer id);
}
