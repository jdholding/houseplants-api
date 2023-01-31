package rc.holding.houseplants.hateoas.embeddedHandler;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.Nullable;

import rc.holding.houseplants.domain.hateoas.impl.HalModel;
/**
 * Interface to handle adding embeddeds to models
 */
public interface EmbeddedHandler<T, D extends HalModel<?>> {
      /**
   * Convert given entity to model and handle adding of embedded resources.
   *
   * @param entity entity to convert to model
   * @param embeddeds list of resources to embed
   */
  default D toModel(T entity, @Nullable Embeddable<D>[] embeddeds) {
    D model = instanciateAssembler().toModel(entity);
    return embeddeds == null ? model : addEmbeddeds(entity, model, embeddeds);
  }

  /** Returns instance of the {@link RepresentationModelAssembler} used to build the model. */
  RepresentationModelAssembler<T, D> instanciateAssembler();

  /**
   * Returns RepresentationModel with all required embeds.
   *
   * @param entity entity source of the model
   * @param model model to add the embeds
   * @param embeddeds list of embeds to add to the models
   */
  D addEmbeddeds(T entity, D model, Embeddable<D>[] embeddeds);

  /**
   * Convert all given entities to models.
   *
   * @see #toModel(Object, Embeddable[])
   * @param entities list of entities to convert to models
   * @param embeddeds list of type of models to embed
   */
  default CollectionModel<D> toCollectionModel(
      Iterable<? extends T> entities, @Nullable Embeddable<D>[] embeddeds) {
    return StreamSupport.stream(entities.spliterator(), false)
        .map(entity -> this.toModel(entity, embeddeds))
        .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
  }
}
