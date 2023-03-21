package rc.holding.houseplants.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import rc.holding.houseplants.hateoas.util.PhotoReader;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
   @Autowired
   DataSource dataSource;
    
    private AppProperties appProperties; 

    public AppConfig(AppProperties appProperties){
        this.appProperties = appProperties; 
    }

//    @Bean
//    @Profile("dev")
//    public PhotoReader devPhotoReader() {
//        return path -> this.getClass().getResourceAsStream("/dev-sample.jpg").readAllBytes();
//    }

    @Bean
//    @Profile("!dev")
    public PhotoReader photoReader() {
        return path -> Files.readAllBytes(Paths.get(appProperties.getPhotos().getPhotoPath() + path));
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
