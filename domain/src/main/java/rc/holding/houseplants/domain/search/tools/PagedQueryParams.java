package rc.holding.houseplants.domain.search.tools;

import org.springframework.lang.Nullable;

public interface PagedQueryParams<T> extends QueryParams<T>{
  /** Returns the page number used for pagination, starting at 0. */
  @Nullable
  Integer getPage();

  /** Returns the number if items in a page. */
  @Nullable
  Integer getSize();

  /** Returns the offset, i.e. the starting row in the pageable lookup. */
  default int getOffset() {
    if (getPage() == null || getSize() == null) {
      return 0;
    }

    return getPage() * getSize();
  }
}
