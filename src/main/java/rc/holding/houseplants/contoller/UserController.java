package rc.holding.houseplants.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.repository.api.UserRepository;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    
    private final UserRepository repo; 

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<User> getUsers() {
        var users = repo.findAll();
        return CollectionModel.of(users); 
    }
    
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<User> insertUser(@RequestBody User user) {
        var createdUser =  repo.insert(user); 
        return EntityModel.of(createdUser); 
    }
}
