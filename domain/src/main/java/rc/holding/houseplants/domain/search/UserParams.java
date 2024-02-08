package rc.holding.houseplants.domain.search;

import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.SortField;
import rc.holding.houseplants.domain.search.tools.Sorter;

@Value
@Builder
public class UserParams implements PagedQueryParams<User> {

  String usernameFragment;

  Integer page;
  Integer size;

  @Singular Collection<Sorter<User>> sorters;

  @AllArgsConstructor
  public enum Field implements SortField<User> {
    id("id"),
    username("username"),
    lastname("last_name"),
    email("email");

    @Getter final String dbname;

    public static final Sorter<User> DEFAULT_SORTER = id.asc();

    public static final Map<Field, String> SORTFIELD_MAP =
        SortField.Mapping.of(Field.class)
            .put(Field.id, "id")
            .put(Field.username, "username")
            .put(Field.lastname, "lastname")
            .put(Field.email, "email")
            .get();
  }
}
