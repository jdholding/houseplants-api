package rc.holding.houseplants.domain.search;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.SortField;
import rc.holding.houseplants.domain.search.tools.Sorter;

@Value
@Builder
public class PhotoParams implements PagedQueryParams<Photo> {
    Integer plantId;
    OffsetDateTime dateBefore;
    OffsetDateTime dateAfter;

    Integer page;
    Integer size; 

    @Singular Collection<Sorter<Photo>> sorters;

    @AllArgsConstructor
    public enum Field implements SortField<Photo> {
        plantId("plant_id"),
        dateCreated("date_created");

        @Getter final String dbname;

        public static final Sorter<Photo> DEFAULT_SORTER = plantId.asc();
        public static final Map<Field, String> SORTFIELD_MAP = SortField.Mapping.of(Field.class)
            .put(dateCreated, "dateCreated")
            .put(plantId, "plantId")
            .get();  
        
    }
}
