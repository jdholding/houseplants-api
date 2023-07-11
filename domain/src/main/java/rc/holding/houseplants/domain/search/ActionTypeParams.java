package rc.holding.houseplants.domain.search;

import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;
import rc.holding.houseplants.domain.ActionType;
import rc.holding.houseplants.domain.search.tools.QueryParams;
import rc.holding.houseplants.domain.search.tools.SortField;
import rc.holding.houseplants.domain.search.tools.Sorter;

@Value
@Builder
public class ActionTypeParams implements QueryParams<ActionType> {

  String nameFragment;
  Integer page;
  Integer size;

  @Singular Collection<Sorter<ActionType>> sorters;

  @AllArgsConstructor
  public enum Field implements SortField<ActionType> {
    id("id"),
    label("label");

    @Getter final String dbname;

    public static final Sorter<ActionType> DEFAULT_SORTER = label.asc();

    public static final Map<Field, String> SORTFILED_MAP =
        SortField.Mapping.of(Field.class).put(id, "id").put(label, "label").get();
  }
}
