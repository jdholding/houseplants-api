package rc.holding.houseplants.service.api;

import org.springframework.web.multipart.MultipartFile;

import rc.holding.houseplants.domain.Photo;

public interface PhotoService {
    Photo uploadPhoto(MultipartFile file, Photo photo); 
}
