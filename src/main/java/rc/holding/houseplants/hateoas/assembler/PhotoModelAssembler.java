package rc.holding.houseplants.hateoas.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import rc.holding.houseplants.contoller.PhotoController;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.domain.hateoas.api.PhotoModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class PhotoModelAssembler extends RepresentationModelAssemblerSupport<Photo, PhotoModel> {

    public PhotoModelAssembler() { super(PhotoController.class, PhotoModel.class); }
    @Override
    protected PhotoModel instantiateModel(Photo photo) { return new PhotoModel(photo); }

    @Override
    public PhotoModel toModel(Photo photo) {

        var model =  createModelWithId(photo.getId(), photo);

        model.add(
                linkTo(methodOn(PhotoController.class).getImage(photo.getId()))
                        .withRel("image"));
        model.add(
                linkTo(methodOn(PhotoController.class).getThumbnail(photo.getId()))
                        .withRel("thumbnail"));

        return model;
    }
}
