package rc.holding.houseplants.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import rc.holding.houseplants.domain.Plant;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        
        Plant plant = new Plant(); 
        return "Hello"; 

        
    }

}
