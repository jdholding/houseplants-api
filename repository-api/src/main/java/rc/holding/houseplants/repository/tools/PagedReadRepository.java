package rc.holding.houseplants.repository.tools;

import rc.holding.houseplants.domain.search.tools.Page;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;

/** Interface for Repositories that return paged results */
public interface PagedReadRepository<T, ID> extends ReadRepository<T, ID> {
  /** Returns {@link Page<T>} results matching given {@link PagedQueryParams <T>} params. */
  Page<T> findPageByParams(PagedQueryParams<T> params);
}
