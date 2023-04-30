package rc.holding.houseplants.domain.search.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Value;
import org.springframework.lang.Nullable;

@Value
public class Sorter<T> {
  public enum Order {
    ASC,
    DESC
  }

  SortField<T> field;
  Order order;

  /** Returns Sorter String value as expected by a SQL query. */
  String toSqlString() {
    return field.getDbname() + " " + order.name();
  }

  /**
   * Returns a list of {@link Sorter}s from an array of aliases. This method determines the sort
   * {@link Order} by using DESC if "-" is the prefix of the alias or ASC otherwise. The matching
   * {@link SortField} is found by using the given mapping.
   *
   * @param aliases the strings to transform to {@link Sorter}s
   * @param mapping maps a {@link SortField} with a string alias
   */
  public static <T> Collection<Sorter<T>> ofAliases(
      @Nullable String[] aliases, Map<? extends SortField<T>, String> mapping) {
    return Collections.unmodifiableSet(buildSetOfAliases(aliases, mapping));
  }

  /**
   * Returns a list of {@link Sorter}s from an array of aliases. This method determines the sort
   * {@link Order} by using DESC if "-" is the prefix of the alias or ASC otherwise. The matching
   * {@link SortField} is found by using the given mapping.
   *
   * @param aliases the strings to transform to {@link Sorter}s
   * @param mapping maps a {@link SortField} with a string alias
   * @param defaultSorter {@link Sorter} that is always added if the matching {@link SortField} is
   *     not already present in the list
   */
  public static <T> Collection<Sorter<T>> ofAliases(
      @Nullable String[] aliases,
      Map<? extends SortField<T>, String> mapping,
      Sorter<T> defaultSorter) {

    Set<Sorter<T>> sorters = buildSetOfAliases(aliases, mapping);

    if (sorters.isEmpty()) {
      return Collections.singleton(defaultSorter);
    }

    if (sorters.stream().noneMatch(sorter -> sorter.getField() == defaultSorter.getField())) {
      sorters.add(defaultSorter);
    }

    return Collections.unmodifiableSet(sorters);
  }

  /**
   * Returns a list of {@link Sorter}s from an array of aliases. This method determines the sort
   * {@link Order} by using DESC if "-" is the prefix of the alias or ASC otherwise. The matching
   * {@link SortField} is found by using the given mapping.
   *
   * @param aliases the strings to transform to {@link Sorter}s
   * @param mapping maps a {@link SortField} with a string alias
   * @param defaultSorters List of {@link Sorter} that is always added if the matching {@link
   *     SortField} is not already present in the list
   */
  public static <T> Collection<Sorter<T>> ofAliases(
      @Nullable String[] aliases,
      Map<? extends SortField<T>, String> mapping,
      List<Sorter<T>> defaultSorters) {

    Set<Sorter<T>> sorters = buildSetOfAliases(aliases, mapping);

    if (sorters.isEmpty()) {
      return Collections.unmodifiableList(defaultSorters);
    }

    defaultSorters.forEach(
        defaultSorter -> {
          if (sorters.stream().noneMatch(sorter -> sorter.getField() == defaultSorter.getField())) {
            sorters.add(defaultSorter);
          }
        });

    return Collections.unmodifiableSet(sorters);
  }

  private static <T> Set<Sorter<T>> buildSetOfAliases(
      @Nullable String[] aliases, Map<? extends SortField<T>, String> mapping) {

    if (aliases == null || aliases.length == 0) {
      return Collections.emptySet();
    }

    return Arrays.stream(aliases)
        .map(alias -> ofAlias(alias, mapping))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Returns a {@link Sorter}s from an alias. This method determines the sort {@link Order} by using
   * DESC if "-" is the prefix of the alias or ASC otherwise. The matching {@link SortField} is
   * found by using the given mapping.
   *
   * @param alias the string to transform to {@link Sorter}
   * @param mapping maps a {@link SortField} with a string alias
   */
  public static <T> Sorter<T> ofAlias(String alias, Map<? extends SortField<T>, String> mapping) {

    return alias.startsWith("-")
        ? new Sorter<>(SortField.of(alias.substring(1), mapping), Order.DESC)
        : new Sorter<>(SortField.of(alias, mapping), Order.ASC);
  }
}
