package rc.holding.houseplants.hateoas.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import rc.holding.houseplants.contoller.PhotoController;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.domain.hateoas.api.PhotoModel;

public class PhotoModelAssembler extends RepresentationModelAssemblerSupport<Photo, PhotoModel> {

    public PhotoModelAssembler() { super(PhotoController.class, PhotoModel.class); }
    @Override
    protected PhotoModel instantiateModel(Photo photo) { return new PhotoModel(photo); }

    @Override
    public PhotoModel toModel(Photo photo) { return createModelWithId(photo.getId(), photo); }
}
