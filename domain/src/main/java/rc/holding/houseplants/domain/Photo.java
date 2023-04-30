package rc.holding.houseplants.domain;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Photo {
  Integer id;
  Integer plantId;
  Integer actionId;
  String photoUri;
  String thumbnailUri;
  OffsetDateTime dateCreated;
}
