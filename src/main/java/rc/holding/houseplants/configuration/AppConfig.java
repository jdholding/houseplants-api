package rc.holding.houseplants.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
//    @Autowired
//    DataSource dataSource;
    
    private AppProperties appProperties; 

    public AppConfig(AppProperties appProperties){
        this.appProperties = appProperties; 
    }

//     @Bean
//     public DataSourceTransactionManager transactionManager() {
//         return new DataSourceTransactionManager(dataSource);
//     }
}
