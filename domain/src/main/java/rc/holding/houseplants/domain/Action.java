package rc.holding.houseplants.domain;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Action {
  Integer id;
  String type;
  Integer plantId;
  Integer userId;
  OffsetDateTime dateCreated;
}
