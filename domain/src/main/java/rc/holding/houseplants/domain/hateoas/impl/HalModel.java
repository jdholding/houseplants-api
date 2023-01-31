package rc.holding.houseplants.domain.hateoas.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
/**
 * Adds support for hal model embedding which allows to chace resources to RepresentationModel
 */
public class HalModel<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    @Getter
    @JsonProperty("_embedded")
    @JsonInclude(Include.NON_EMPTY)
    private final Map<String, Object> embedded = new HashMap<>();
  
    /**
     * Adds an object in the '_embedded' hal object.
     *
     * @param rel name of the hal relation
     * @param object to embed
     */
    public void embed(String rel, Object object) {
      embedded.put(rel, object);
    }
  
    /**
     * Adds a CollectionModel in the '_embedded' hal object.
     *
     * @param rel name of the hal relation
     * @param collectionModel to embed
     */
    public void embed(String rel, CollectionModel collectionModel) {
      embedded.put(rel, collectionModel.getContent());
    }
}
