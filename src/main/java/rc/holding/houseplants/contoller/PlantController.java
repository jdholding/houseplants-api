package rc.holding.houseplants.contoller;

import java.io.IOException;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.domain.hateoas.api.PlantModel;
import rc.holding.houseplants.domain.search.PlantParams;
import rc.holding.houseplants.domain.search.PlantParams.Field;
import rc.holding.houseplants.domain.search.tools.Page;
import rc.holding.houseplants.domain.search.tools.Sorter;
import rc.holding.houseplants.exception.ResourceNotFoundException;
import rc.holding.houseplants.hateoas.embeddedHandler.Embeddable;
import rc.holding.houseplants.hateoas.embeddedHandler.PlantModelEmbeddedHandler;
import rc.holding.houseplants.hateoas.embeddedHandler.PlantModelEmbeddedHandler.Embedded;
import rc.holding.houseplants.hateoas.util.PagedModelHelper;
import rc.holding.houseplants.repository.api.PlantRepository;
import rc.holding.houseplants.service.api.PhotoService;


@RestController
@RequestMapping("/plants")
@AllArgsConstructor
public class PlantController {

    private final PlantRepository repo;
    private final PhotoService photoService;
    private final PlantModelEmbeddedHandler plantModelHandler; 

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<PlantModel> getPlants(
        @RequestParam(value = "parentId", required = false) Integer parentId,
        @RequestParam(value = "trefleId", required = false) Integer trefleId,
        @RequestParam(value = "userId", required = false) Integer userId,
        @RequestParam(value = "nameFragment", required = false) String nameFragment,
        @RequestParam(value = "embed", required = false) Embedded[] embeds,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size,
        @RequestParam(value = "sort", required = false) String[] sorters) {

        PlantParams params = PlantParams.builder()
                                .parentId(parentId)
                                .trefleId(trefleId)
                                .nameFragment(nameFragment)
                                .page(page)
                                .size(size)
                                .sorters(Sorter.ofAliases(sorters, Field.SORTFIELD_MAP, Field.DEFAULT_SORTER)) 
                                .build();   

        var plantPage = repo.findPageByParams(params);
        var plantResource = plantModelHandler.toCollectionModel(plantPage.getContent(), embeds);
        return PagedModelHelper.<PlantModel>builder()
            .controllerClass(this.getClass())
            .queryParam("sort", sorters)
            .collectionModel(plantResource)
            .pageMetadata(plantPage.getMetadata())
            .build()
            .toPagedModel();   
    }

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<PlantModel> getPlantById(@PathVariable("id") Integer id, 
        @RequestParam(value = "embed", required=false) Embedded[] embed) {

        var plant = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        var plantModel = plantModelHandler.toModel(plant, embed); 
        return EntityModel.of(plantModel);   
    }

    @PostMapping(path="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<PlantModel> addPlant(@RequestParam(value="photo", required=false) MultipartFile photoFile,
        @RequestParam(value="trefleId", required=false) Integer trefleId,
        @RequestParam(value="parentId", required=false) Integer parentId,
        @RequestParam(value="userId") Integer userId,
        @RequestParam(value="family", required=false) String family,
        @RequestParam(value="genus", required=false) String genus,
        @RequestParam(value="species", required=false) String species,
        @RequestParam(value="commonName", required=false) String commonName) throws IOException {
            var plant = Plant.builder()
                .trefleId(trefleId)
                .parentId(parentId)
                .userId(userId)
                .family(family)
                .genus(genus)
                .species(species)
                .commonName(commonName)
                .build(); 
        var plantId = repo.insert(plant);
        var photo = Photo.builder().plantId(plantId).build();
        if(photoFile != null) {
            photoService.uploadPhoto(photoFile, photo);
        }
        var plantInserted = repo.findById(plantId).orElseThrow(() -> new ResourceNotFoundException(plantId));
        var plantModel = plantModelHandler.toModel(plantInserted, null);   
        return EntityModel.of(plantModel);  
    }

    @PatchMapping(path="/{id}/edit", consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Plant> editPlant(@PathVariable("id")Integer id, @RequestBody Plant plant){
        plant.setId(id); 
        var editedPlant = repo.update(plant);
        return EntityModel.of(editedPlant);  
    }

}
