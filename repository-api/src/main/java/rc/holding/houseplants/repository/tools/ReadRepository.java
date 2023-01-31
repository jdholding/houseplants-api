package rc.holding.houseplants.repository.tools;

import java.util.Optional;

import rc.holding.houseplants.domain.search.tools.QueryParams;

public interface ReadRepository<T, ID> {
  /** Returns the entity matching the given id. */
  Optional<T> findById(ID id);

  /**
   * Returns <code>true</code> if an entity with the given <code>id</code> exists, <code>false
   * </code> otherwise.
   */
  default boolean existsById(ID id) {
    throw new UnsupportedOperationException("you need to implement this method");
  }

  /** Counts the total number of entities in the repository for given params. */
  default int countByParams(QueryParams<T> params) {
    throw new UnsupportedOperationException("you need to implement this method");
  }

  /** Returns {@link Iterable<T>} of entities matching given params. */
  Iterable<T> findAllByParams(QueryParams<T> params);
}
