package rc.holding.houseplants.contoller;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.domain.hateoas.api.PhotoModel;
import rc.holding.houseplants.domain.search.PhotoParams;
import rc.holding.houseplants.domain.search.tools.Sorter;
import rc.holding.houseplants.exception.ResourceNotFoundException;
import rc.holding.houseplants.hateoas.embeddedHandler.PhotoModelEmbeddedHandler;
import rc.holding.houseplants.hateoas.util.PagedModelHelper;
import rc.holding.houseplants.hateoas.util.PhotoReader;
import rc.holding.houseplants.repository.api.PhotoRepository;
import rc.holding.houseplants.service.api.PhotoService;

import java.io.IOException;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/photos")
@AllArgsConstructor
public class PhotoController {
//    public PhotoController(PhotoRepository repo, PhotoService service, PhotoModelEmbeddedHandler photoModelHandler){
//        this.repo = repo;
//        this.service = service;
//        this. photoModelHandler = photoModelHandler;
//    }

    private final PhotoRepository repo;
    private final PhotoService service;
    private final PhotoModelEmbeddedHandler photoModelHandler;

    private final PhotoReader photoReader;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<PhotoModel> getPhotos(
            @RequestParam(value = "plantId", required = false) Integer plantId,
            @RequestParam(value = "dateBefore", required = false) OffsetDateTime dateBefore,
            @RequestParam(value = "dateAfter", required = false) OffsetDateTime dateAfter,
            @RequestParam(value = "embed", required = false)PhotoModelEmbeddedHandler.Embedded[] embeds,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false) String[] sorters) {

        var params = PhotoParams.builder()
                .plantId(plantId)
                .dateBefore(dateBefore)
                .dateAfter(dateAfter)
                .page(page)
                .size(size)
                .sorters(Sorter.ofAliases(sorters, PhotoParams.Field.SORTFIELD_MAP, PhotoParams.Field.DEFAULT_SORTER))
                .build();

        var photoPage = repo.findPageByParams(params);
        var photoResource = photoModelHandler.toCollectionModel(photoPage.getContent(), embeds);
        return PagedModelHelper.<PhotoModel>builder()
                .controllerClass(this.getClass())
                .queryParam("sort", sorters)
                .collectionModel(photoResource)
                .pageMetadata(photoPage.getMetadata())
                .build()
                .toPagedModel();

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<PhotoModel> getPhotoById(@PathVariable("id") Integer id,
            @RequestParam(value = "embed", required=false) PhotoModelEmbeddedHandler.Embedded[] embed){
        var photo = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        var photoModel = photoModelHandler.toModel(photo, embed);
        return EntityModel.of(photoModel);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EntityModel<PhotoModel> insertPhoto(@RequestParam("photo") MultipartFile photoFile, @RequestParam("plantId") Integer plantId) throws IOException {
        var newPhoto = Photo.builder().plantId(plantId).build();
        var photo = service.uploadPhoto(photoFile, newPhoto);
        var photoModel = photoModelHandler.toModel(photo, null);
        return EntityModel.of(photoModel);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable("id") Integer id) {

        var photo = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        try {
            return ResponseEntity.ok(photoReader.read(photo.getPhotoUri()));
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    @GetMapping(value = "/{id}/thumbnail", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getThumbnail(@PathVariable("id") Integer id) {
        var photo = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        try {
            return ResponseEntity.ok(photoReader.read(photo.getThumbnailUri()));
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
