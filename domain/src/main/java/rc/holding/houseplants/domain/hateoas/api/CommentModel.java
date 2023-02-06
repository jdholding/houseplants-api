package rc.holding.houseplants.domain.hateoas.api;

import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;
import rc.holding.houseplants.domain.Comment;
import rc.holding.houseplants.domain.hateoas.impl.HalModel;

import java.time.LocalDateTime;

@Relation(value = "comment", collectionRelation = "comments")
public class CommentModel extends HalModel<CommentModel> {
    @Getter private final Integer userId;
    @Getter private final Integer plantId;
    @Getter private final LocalDateTime dateCreated;
    @Getter private final String text;
    @Getter private final Boolean isHidden;

    public CommentModel(Comment comment) {
        this.userId = comment.getUserId();
        this.plantId = comment.getPlantId();
        this.dateCreated = comment.getDateCreated().toLocalDateTime();
        this.text = comment.getText();
        this.isHidden = comment.getIsHidden();
    }


}
