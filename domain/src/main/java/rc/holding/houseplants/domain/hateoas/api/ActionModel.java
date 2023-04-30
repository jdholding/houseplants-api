package rc.holding.houseplants.domain.hateoas.api;

import java.time.OffsetDateTime;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;
import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.hateoas.impl.HalModel;

@Relation(value = "action", collectionRelation = "actions")
public class ActionModel extends HalModel<ActionModel> {
  @Getter private final Integer plantId;
  @Getter private final String type;
  @Getter private final Integer userId;
  @Getter private final OffsetDateTime dateCretaed;

  public ActionModel(Action action) {
    plantId = action.getPlantId();
    type = action.getType();
    userId = action.getUserId();
    dateCretaed = action.getDateCreated();
  }
}
