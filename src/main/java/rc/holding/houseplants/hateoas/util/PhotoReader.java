package rc.holding.houseplants.hateoas.util;

import java.io.IOException;

@FunctionalInterface
public interface PhotoReader {

  /**
   * Returns the photo as an array of bytes.
   *
   * @param path relative path of the photo
   */
  byte[] read(String path) throws IOException;
}
