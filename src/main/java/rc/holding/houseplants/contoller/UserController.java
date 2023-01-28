package rc.holding.houseplants.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.exception.ResourceNotFoundException;
import rc.holding.houseplants.repository.api.UserRepository;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    
    private final UserRepository repo; 

    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<User> getUsers() {
        var users = repo.findAll();
        return CollectionModel.of(users); 
    }
    
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<User> getUserById(@PathVariable Integer id) {
        var user = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return EntityModel.of(user);  
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<User> insertUser(@RequestBody User user) {
        var createdUser =  repo.insert(user); 
        return EntityModel.of(createdUser); 
    }

    @PatchMapping(path="/{id}/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<User> editUser(@PathVariable Integer id, @RequestBody User user){
        user.setId(id); 
        var updatedUser = repo.update(user); 
        return EntityModel.of(updatedUser); 
    }

    @DeleteMapping(path="/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(Integer id) {
        repo.delete(id);
    }
 
}
