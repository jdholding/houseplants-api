package rc.holding.houseplants.contoller;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.ActionType;
import rc.holding.houseplants.domain.hateoas.api.ActionModel;
import rc.holding.houseplants.domain.search.ActionParams;
import rc.holding.houseplants.domain.search.ActionTypeParams;
import rc.holding.houseplants.domain.search.ActionTypeParams.Field;
import rc.holding.houseplants.domain.search.tools.Sorter;
import rc.holding.houseplants.exception.ResourceNotFoundException;
import rc.holding.houseplants.hateoas.embeddedHandler.ActionModelEmbeddedHandler;
import rc.holding.houseplants.hateoas.util.PagedModelHelper;
import rc.holding.houseplants.repository.api.ActionRepository;

@RestController
@RequestMapping("/actions")
@AllArgsConstructor
public class ActionController {

  private final ActionRepository repo;
  private final ActionModelEmbeddedHandler actionModelHandler;

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  CollectionModel<ActionModel> getActions(
      @RequestParam(value = "plantId", required = false) Integer plantId,
      @RequestParam(value = "userId", required = false) Integer userId,
      @RequestParam(value = "typeId", required = false) Integer typeId,
      @RequestParam(value = "dateBefore", required = false) OffsetDateTime dateBefore,
      @RequestParam(value = "dateAfter", required = false) OffsetDateTime dateAfter,
      @RequestParam(value = "embed", required = false) ActionModelEmbeddedHandler.Embedded[] embeds,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "sort", required = false) String[] sorters) {

    var params =
        ActionParams.builder()
            .plantId(plantId)
            .userId(userId)
            .typeId(typeId)
            .dateBefore(dateBefore)
            .dateAfter(dateAfter)
            .page(page)
            .size(size)
            .sorters(
                Sorter.ofAliases(
                    sorters, ActionParams.Field.SORTFIELD_MAP, ActionParams.Field.DEFAULT_SORTER))
            .build();

    var actionPage = repo.findPageByParams(params);
    var actionResource = actionModelHandler.toCollectionModel(actionPage.getContent(), embeds);
    return PagedModelHelper.<ActionModel>builder()
        .controllerClass(this.getClass())
        .queryParam("sort", sorters)
        .collectionModel(actionResource)
        .pageMetadata(actionPage.getMetadata())
        .build()
        .toPagedModel();
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public EntityModel<ActionModel> insertAction(@RequestBody Action action) {
    var actionCreatedId = repo.insert(action);
    return getActionById(actionCreatedId, null);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  EntityModel<ActionModel> getActionById(
      @PathVariable("id") Integer id,
      @RequestParam(value = "embed", required = false)
          ActionModelEmbeddedHandler.Embedded[] embed) {
    var action = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    var actionModel = actionModelHandler.toModel(action, embed);
    return EntityModel.of(actionModel);
  }

  @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
  List<ActionType> getActionTypes(
      @RequestParam(value = "nameFragment", required = false) String nameFragment,
      @RequestParam(value = "sort", required = false) String[] sorters) {
    var params =
        ActionTypeParams.builder()
            .nameFragment(nameFragment)
            .sorters(Sorter.ofAliases(sorters, Field.SORTFILED_MAP, Field.DEFAULT_SORTER))
            .build();
    return repo.findTypesPageByParams(params);
  }
}
