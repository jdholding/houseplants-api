package rc.holding.houseplants.domain.hateoas.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
/**
 * Adds support to embed resources in a hal collection (which is not available in RepresentationModel)
 */
public class HalCollectionModel <T extends RepresentationModel> extends HalModel {
    public HalCollectionModel() {
        this.embedded = new HashMap<>();
      }
    
      public HalCollectionModel(String rel, Iterable<T> content, Link... links) {
        this.embedded = new HashMap<>();
        this.add(links);
    
        if (content instanceof CollectionModel) {
          this.embed(rel, ((CollectionModel) content).getContent());
        } else {
          this.embed(rel, content);
        }
      }
    
      public HalCollectionModel(Map<String, Object> embedded, Iterable<Link> links) {
        this.embedded = embedded;
        this.add(links);
      }
    
      @Getter
      @JsonProperty("_embedded")
      @JsonInclude(Include.NON_EMPTY)
      private final Map<String, Object> embedded;
    
      /**
       * Adds any object in the '_embedded' hal object.
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

