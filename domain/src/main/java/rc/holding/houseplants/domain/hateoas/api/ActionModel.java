package rc.holding.houseplants.domain.hateoas.api;

import java.time.OffsetDateTime;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;
import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.ActionType;
import rc.holding.houseplants.domain.hateoas.impl.HalModel;

@Relation(value = "action", collectionRelation = "actions")
public class ActionModel extends HalModel<ActionModel> {
  @Getter private final Integer plantId;
  @Getter private final Integer userId;
  @Getter private final OffsetDateTime dateCretaed;
  @Getter private final ActionType type;

  public ActionModel(Action action) {
    plantId = action.getPlantId();
    userId = action.getUserId();
    dateCretaed = action.getDateCreated();
    type = action.getType();
  }
}
