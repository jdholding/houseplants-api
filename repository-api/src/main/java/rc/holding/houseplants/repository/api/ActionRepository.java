package rc.holding.houseplants.repository.api;

import java.util.List;
import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.ActionType;
import rc.holding.houseplants.domain.search.tools.QueryParams;
import rc.holding.houseplants.repository.tools.PagedReadRepository;

public interface ActionRepository extends PagedReadRepository<Action, Integer> {

  Integer insert(Action action);

  Action update(Action action);

  List<ActionType> findTypesPageByParams(QueryParams<ActionType> params);
}
