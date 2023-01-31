package rc.holding.houseplants.domain.search;

import java.util.Collection;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.SortField;
import rc.holding.houseplants.domain.search.tools.Sorter;

@Value
@Builder
public class PlantParams implements PagedQueryParams<Plant>{
    Integer parentId;
    Integer trefleId;
    String nameFragment; 

    Integer page; 
    Integer size; 

    @Singular Collection<Sorter<Plant>> sorters; 

    @AllArgsConstructor
    public enum Field implements SortField<Plant> {
        parentId("parent_id"),
        dateCreated("date_created");

        @Getter final String dbname; 

        public static final Sorter<Plant> DEFAULT_SORTER = parentId.asc();
        public static final Map<Field, String> SORTFIELD_MAP = SortField.Mapping.of(Field.class)
        .put(Field.parentId, "parentId")
        .put(Field.dateCreated, "dateCreated")
        .get();
    }
}
