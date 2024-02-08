package rc.holding.houseplants.domain;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
  Integer id;
  Integer plantId;
  Integer userId;
  OffsetDateTime dateCreated;
  String text;
  Boolean isHidden;
}
