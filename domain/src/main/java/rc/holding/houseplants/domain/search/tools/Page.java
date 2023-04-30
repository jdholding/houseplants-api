package rc.holding.houseplants.domain.search.tools;

import java.util.Collections;
import java.util.List;
import lombok.Value;

/** Container for paged lists, containing the results and the metadata of the pageable query. */
@Value
public class Page<T> {
  /** the results collection */
  Iterable<T> content;

  /** the pageable search metadata */
  Metadata metadata;

  public Page(List<T> content, int page, int size, int totalCount, int totalPages) {
    this.content = Collections.unmodifiableCollection(content);
    this.metadata = new Metadata(page, size, content.size(), totalCount, totalPages);
  }

  public Page(List<T> content, int page, int size, int totalCount) {
    this.content = Collections.unmodifiableCollection(content);
    this.metadata =
        new Metadata(page, size, content.size(), totalCount, getTotalPages(totalCount, size));
  }

  private int getTotalPages(int totalCount, int size) {
    if (totalCount == 0) {
      return 0;
    }

    if (size > totalCount || size == totalCount) {
      return 1;
    }

    int divider = Math.floorDiv(totalCount, size);

    if (divider == 1) {
      return 2;
    }

    int divisionRest = totalCount % divider;
    return divisionRest > 0 ? divider + 1 : divider;
  }

  @Value
  public static class Metadata {

    /** the number of the page required by the user */
    int number;

    /** the size of results per page required by the user */
    int userSize;

    /** the actual size of results returned by the query */
    int size;

    /** the total number of elements in the database */
    int totalElements;

    /** the total number of pages */
    int totalPages;
  }
}
