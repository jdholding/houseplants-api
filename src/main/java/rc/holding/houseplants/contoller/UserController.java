package rc.holding.houseplants.contoller;

import lombok.AllArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.domain.hateoas.api.UserModel;
import rc.holding.houseplants.domain.search.UserParams;
import rc.holding.houseplants.domain.search.UserParams.Field;
import rc.holding.houseplants.domain.search.tools.Sorter;
import rc.holding.houseplants.exception.ResourceNotFoundException;
import rc.holding.houseplants.hateoas.embeddedHandler.UserModelEmbeddedHandler;
import rc.holding.houseplants.hateoas.embeddedHandler.UserModelEmbeddedHandler.Embedded;
import rc.holding.houseplants.hateoas.util.PagedModelHelper;
import rc.holding.houseplants.repository.api.UserRepository;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final UserRepository repo;
  private final UserModelEmbeddedHandler userModelHandler;

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<UserModel> getUsers(
      @RequestParam(value = "usernameFragment", required = false) String usernameFragment,
      @RequestParam(value = "embed", required = false) Embedded[] embeds,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "sort", required = false) String[] sorters) {
    var params =
        UserParams.builder()
            .usernameFragment(usernameFragment)
            .page(page)
            .size(size)
            .sorters(Sorter.ofAliases(sorters, Field.SORTFIELD_MAP, Field.DEFAULT_SORTER))
            .build();

    var userPage = repo.findPageByParams(params);
    var userResource = userModelHandler.toCollectionModel(userPage.getContent(), embeds);
    return PagedModelHelper.<UserModel>builder()
        .controllerClass(this.getClass())
        .queryParam("sort", sorters)
        .collectionModel(userResource)
        .pageMetadata(userPage.getMetadata())
        .build()
        .toPagedModel();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public EntityModel<User> getUserById(@PathVariable Integer id) {
    var user = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    return EntityModel.of(user);
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public EntityModel<User> insertUser(@RequestBody User user) {
    var createdUserId = repo.insert(user);
    return getUserById(createdUserId);
  }

  @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public EntityModel<User> editUser(@PathVariable Integer id, @RequestBody User user) {
    user.setId(id);
    var updatedUser = repo.update(user);
    return EntityModel.of(updatedUser);
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteUser(Integer id) {
    repo.delete(id);
  }
}
