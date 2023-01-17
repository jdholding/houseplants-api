package rc.holding.houseplants.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Data
public class AppProperties {
    
}
