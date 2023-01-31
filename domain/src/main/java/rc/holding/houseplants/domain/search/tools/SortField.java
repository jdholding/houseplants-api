package rc.holding.houseplants.domain.search.tools;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
/**
 * Interface to define a sort field
 */
public interface SortField<T> {
      /** Returns the field name in the database. */
  String getDbname();

  /** Returns a {@link Sorter} with ascending order. */
  default Sorter<T> asc() {
    return new Sorter<>(this, Sorter.Order.ASC);
  }

  /** Returns a {@link Sorter} with descending order. */
  default Sorter<T> desc() {
    return new Sorter<>(this, Sorter.Order.DESC);
  }

  static <T> SortField<T> of(String value, Map<? extends SortField<T>, String> mapping) {
    return mapping.entrySet().stream()
        .filter(entry -> entry.getValue().equalsIgnoreCase(value))
        .findFirst()
        .map(Entry::getKey)
        .orElseThrow(() -> new InvalidSortFieldException(value));
  }

  /** Helper class to create an immutable EnumMap with a fluent API. */
  @AllArgsConstructor
  class Mapping<E extends Enum<E>> {

    private EnumMap<E, String> map;

    public static <E extends Enum<E>> Mapping<E> of(Class<E> enumClass) {
      return new Mapping<>(new EnumMap<>(enumClass));
    }

    public Mapping<E> put(E key, String value) {
      map.put(key, value);
      return this;
    }

    public Map<E, String> get() {
      return Collections.unmodifiableMap(map);
    }
  }
}
