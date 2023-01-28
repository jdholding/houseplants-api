package rc.holding.houseplants.repository.api;

import java.util.List;
import java.util.Optional;

import rc.holding.houseplants.domain.Photo;

public interface PhotoRepository {
    
    List<Photo> findAll(); 

    Optional<Photo> findById(Integer id);
    
    Photo insert(Photo photo); 
}
