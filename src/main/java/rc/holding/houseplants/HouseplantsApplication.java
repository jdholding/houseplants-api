package rc.holding.houseplants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"rc.holding.houseplants"})
public class HouseplantsApplication {

  public static void main(String[] args) {
    SpringApplication.run(HouseplantsApplication.class, args);
  }
}
