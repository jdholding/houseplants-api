package rc.holding.houseplants.domain.hateoas.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.server.core.Relation;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.domain.hateoas.impl.HalModel;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Relation(value = "photo", collectionRelation = "photos")
public class PhotoModel extends HalModel<PhotoModel> {
    @Value("${app.url.context}")
    public String contextPath;
    @Getter private final Integer plantId;
    @Getter private final String photoUrl;

    @Getter private final String thumbnailUrl;

    @Getter private final LocalDateTime dateCreated;

    public PhotoModel(Photo photo) {
        this.plantId = photo.getPlantId();
        this.photoUrl =  contextPath + photo.getPhotoUri();
        this.thumbnailUrl = contextPath + photo.getThumbnailUri();
        this.dateCreated =  photo.getDateCreated().toLocalDateTime();
    }
}
