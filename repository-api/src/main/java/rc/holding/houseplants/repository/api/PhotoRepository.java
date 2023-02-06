package rc.holding.houseplants.repository.api;

import java.util.List;
import java.util.Optional;

import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.repository.tools.PagedReadRepository;

public interface PhotoRepository extends PagedReadRepository<Photo, Integer> {
    
    Integer insert(Photo photo); 
}
