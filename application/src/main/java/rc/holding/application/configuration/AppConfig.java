package rc.holding.application.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
    // @Autowired DataSource dataSource;
    
    private AppProperties appProperties; 

    public AppConfig(AppProperties appProperties){
        this.appProperties = appProperties; 
    }

    // @Bean
    // public DataSourceTransactionManager transactionManager() {
    //     return new DataSourceTransactionManager(dataSource); 
    // }
}
