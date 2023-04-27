package rc.holding.houseplants.repository.api;

import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.ActionType;
import rc.holding.houseplants.repository.tools.PagedReadRepository;

import java.util.List;

public interface ActionRepository extends PagedReadRepository<Action, Integer> {

    Integer insert(Action action);

    Action update(Action action);

    List<ActionType> findAllTypes();
}
