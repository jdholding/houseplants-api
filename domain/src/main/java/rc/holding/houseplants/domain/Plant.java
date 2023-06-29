package rc.holding.houseplants.domain;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Plant {
  Integer id;
  Integer trefleId;
  Integer parentId;
  Integer userId;
  String family;
  String genus;
  String species;
  String commonName;
  OffsetDateTime dateCreated;
}
