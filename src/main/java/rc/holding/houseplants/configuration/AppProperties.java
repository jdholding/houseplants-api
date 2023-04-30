package rc.holding.houseplants.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Validated
@Data
public class AppProperties {
  private final Photos photos = new Photos();
  private final Url url = new Url();

  @Data
  public static class Photos {

    private String photoPath;
    private String originalPath;
    private String thumbnailPath;
  }

  @Data
  public static class Url {
    private String context;
  }
}
