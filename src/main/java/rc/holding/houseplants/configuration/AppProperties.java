package rc.holding.houseplants.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


import lombok.Data;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Validated
@Data
public class AppProperties {
    private final Photos photos = new Photos();

    @Data
    public static class Photos {
        private String originalPath;
        private String thumbnailPath;
    }
}
