package rc.holding.houseplants; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = {"rc.holding"})
public class HouseplantsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseplantsApplication.class, args);
	}

}
