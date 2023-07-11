package rc.holding.houseplants.domain;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Action {
  Integer id;
  Integer plantId;
  Integer userId;
  OffsetDateTime dateCreated;
  ActionType type;
}
