package rc.holding.houseplants.domain;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {
  Integer id;
  Integer plantId;
  Integer userId;
  Integer typeId;
  OffsetDateTime dateCreated;
  ActionType type;
}
