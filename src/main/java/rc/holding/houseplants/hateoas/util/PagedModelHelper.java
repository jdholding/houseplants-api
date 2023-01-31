package rc.holding.houseplants.hateoas.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Singular;
import rc.holding.houseplants.domain.hateoas.impl.HalCollectionModel;
import rc.holding.houseplants.domain.hateoas.impl.HalPagedModel;
import rc.holding.houseplants.domain.search.tools.Page;
import rc.holding.houseplants.domain.search.tools.Page.Metadata;

/**
 * Helper to provide a builder to create a Spring hateoas paged model by adding links
 * 'prev', 'next', 'first', and 'last' and the page metadata
 */
@Builder
public class PagedModelHelper<T extends RepresentationModel<? extends T>> {
    Class<?> controllerClass;
    String path;
    @Singular Map<String, ?> pathParams;
    @Singular Map<String, ?> queryParams;
    CollectionModel<T> collectionModel;
    HalCollectionModel<T> halCollectionModel;
    Page.Metadata pageMetadata;
    @Singular List<Link> links;
  
    public PagedModel<T> toPagedModel() {
      return PagedModel.of(
          collectionModel.getContent(), toSpringPageMetadata(pageMetadata), getPageLinks());
    }
  
    public HalPagedModel<T> toHalPagedModel() {
      return new HalPagedModel<>(
          halCollectionModel, getPageLinks(), toSpringPageMetadata(pageMetadata));
    }
  
    private List<Link> getPageLinks() {
      var pageLinks =
          links == null || pageMetadata.getSize() == 0
              ? new ArrayList<Link>()
              : new ArrayList<>(links);
      var totalPages = pageMetadata.getTotalPages();
      long currentPage = pageMetadata.getNumber();
      long userSize = pageMetadata.getUserSize();
  
      String paramsString =
          queryParams.entrySet().stream()
              .filter(entry -> entry.getValue() != null)
              .map(entry -> entry.getKey() + "=" + paramValueToString(entry.getValue()))
              .collect(Collectors.joining("&"));
  
      var path = getPath();
  
      pageLinks.add(
          createPageLink(
              IanaLinkRelations.SELF, controllerClass, userSize, currentPage, paramsString, path));
  
      if (currentPage > 0) {
        pageLinks.add(
            createPageLink(
                IanaLinkRelations.PREV,
                controllerClass,
                userSize,
                currentPage - 1,
                paramsString,
                path));
      }
  
      if (currentPage < totalPages - 1) {
        pageLinks.add(
            createPageLink(
                IanaLinkRelations.NEXT,
                controllerClass,
                userSize,
                currentPage + 1,
                paramsString,
                path));
      }
  
      if (totalPages > 1) {
        pageLinks.add(
            createPageLink(
                IanaLinkRelations.FIRST, controllerClass, userSize, 0, paramsString, path));
        pageLinks.add(
            createPageLink(
                IanaLinkRelations.LAST,
                controllerClass,
                userSize,
                totalPages - 1,
                paramsString,
                path));
      }
  
      return pageLinks;
    }
  
    private static String paramValueToString(Object value) {
      if (value instanceof Iterable<?>) {
        return StreamSupport.stream(((Iterable<?>) value).spliterator(), false)
            .map(Object::toString)
            .collect(Collectors.joining(","));
      } else if (value instanceof Object[]) {
        return Arrays.stream((Object[]) value).map(Object::toString).collect(Collectors.joining(","));
      } else {
        return value.toString();
      }
    }
  
    private static Link createPageLink(
        LinkRelation rel,
        Class<?> controllerClass,
        long userSize,
        long page,
        @Nullable String params,
        @Nullable String path) {
      String queryParams =
          params == null
              ? String.format("?page=%d&size=%d", page, userSize)
              : String.format("?page=%d&size=%d&%s", page, userSize, params);
  
      return path == null
          ? linkTo(controllerClass).slash(queryParams).withRel(rel)
          : linkTo(controllerClass).slash(path).slash(queryParams).withRel(rel);
    }
  
    private String getPath() {
      if (path == null) {
        return null;
      }
  
      if (pathParams == null || pathParams.isEmpty()) {
        return path;
      }
  
      String result = null;
  
      for (Map.Entry<String, ?> pathParam : pathParams.entrySet()) {
        result = path.replace("{" + pathParam.getKey() + "}", pathParam.getValue().toString());
      }
  
      return result;
    }
  
    private static PageMetadata toSpringPageMetadata(Metadata pageMeta) {
      return new PageMetadata(
          pageMeta.getSize(),
          pageMeta.getNumber(),
          pageMeta.getTotalElements(),
          pageMeta.getTotalPages());
    }
}
