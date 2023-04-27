package rc.holding.houseplants.domain.search;

import lombok.*;
import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.SortField;
import rc.holding.houseplants.domain.search.tools.Sorter;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Map;

@Value
@Builder
public class ActionParams implements PagedQueryParams<Action> {

    Integer plantId;

    Integer typeId;
    Integer userId;
    OffsetDateTime dateBefore;

    OffsetDateTime dateAfter;
    Integer page;

    Integer size;

    @Singular Collection<Sorter<Action>> sorters;

    @AllArgsConstructor
    public enum Field implements SortField<Action> {
        plantId("plant_id"),
        userId("user_id"),
        dateCreated("date_created");

        @Getter final String dbname;

        public static final Sorter<Action> DEFAULT_SORTER = dateCreated.desc();

        public static final Map<Field, String> SORTFIELD_MAP = SortField.Mapping.of(Field.class)
                .put(plantId, "plantId")
                .put(userId, "userId")
                .put(dateCreated, "dateCreated")
                .get();
    }


}
