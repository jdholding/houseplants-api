package rc.holding.houseplants.domain.hateoas.api;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.domain.hateoas.impl.HalModel;

@Relation(value = "photo", collectionRelation = "photos")
public class PhotoModel extends HalModel<PhotoModel> {
  @Getter private final Integer plantId;

  @Getter private final LocalDateTime dateCreated;

  public PhotoModel(Photo photo) {
    this.plantId = photo.getPlantId();
    this.dateCreated = photo.getDateCreated().toLocalDateTime();
  }
}
