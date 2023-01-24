package rc.holding.houseplants.repository.api;

import java.util.List;

import rc.holding.houseplants.domain.User;

public interface UserRepository {
    List<User> findAll(); 

    User insert(User user); 
}
