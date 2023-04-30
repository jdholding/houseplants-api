package rc.holding.houseplants.domain.search.tools;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;

/**
 * Interface used to type query params accepted by {@link ReadService} and {@link ReadRepository}
 * methods that defines sort capacities.
 */
public interface QueryParams<T> {
  /** Returns the list of Sorter used for the paged query. */
  @Nullable
  Collection<Sorter<T>> getSorters();

  /**
   * Returns list of sorter as expected by an sql ORDER BY query string. ie. list of db fields
   * followed by order type ASC or DESC and separated by commas.
   */
  @Nullable
  default String getOrderByFields() {
    if (getSorters() == null || getSorters().isEmpty()) {
      return null;
    }

    return getSorters().stream().map(Sorter::toSqlString).collect(Collectors.joining(", "));
  }
}
