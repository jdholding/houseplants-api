package rc.holding.houseplants.service.api;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import rc.holding.houseplants.domain.Photo;

public interface PhotoService {
    Photo uploadPhoto(MultipartFile file, Photo photo) throws IOException; 
}
