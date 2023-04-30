package rc.holding.houseplants.domain.hateoas.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Iterables;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.RepresentationModel;

public class HalPagedModel<T extends RepresentationModel> extends HalCollectionModel<T> {
  public HalPagedModel(
      HalCollectionModel<T> halCollectionModel, Iterable<Link> links, PageMetadata pageMetadata) {
    super(halCollectionModel.getEmbedded(), Iterables.concat(halCollectionModel.getLinks(), links));
    this.pageMetadata = pageMetadata;
  }

  @Getter
  @Setter
  @JsonProperty("page")
  @JsonInclude(Include.NON_NULL)
  private PageMetadata pageMetadata;
}
