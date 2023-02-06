package rc.holding.houseplants.domain.search;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import rc.holding.houseplants.domain.Comment;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.SortField;
import rc.holding.houseplants.domain.search.tools.Sorter;

@Value
@Builder
public class CommentParams implements PagedQueryParams<Comment> {
    Integer plantId;
    Integer userId; 
    Boolean isHidden;
    
    Integer page; 
    Integer size; 

    @Singular Collection<Sorter<Comment>> sorters; 

    @AllArgsConstructor
    public enum Field implements SortField<Comment> {
        plantId("plant_id"),
        userId("user_id"), 
        dateCreated("date_created");

        @Getter final String dbname;

        public static final Sorter<Comment> DEFAULT_SORTER = dateCreated.desc();
        public static final Map<Field, String> SORTFIELD_MAP = SortField.Mapping.of(Field.class)
            .put(plantId, "plantId")
            .put(userId, "userId")
            .put(dateCreated, "dateCreated")
            .get();  
    }
}
