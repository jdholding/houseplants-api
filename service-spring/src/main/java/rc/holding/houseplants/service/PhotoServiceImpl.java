package rc.holding.houseplants.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.repository.api.PhotoRepository;
import rc.holding.houseplants.service.api.PhotoService;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    
    private final PhotoRepository repo; 

    @Override
    public Photo uploadPhoto(MultipartFile file, Photo photo){
        // StringBuilder fileNames = new StringBuilder();
        // Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        // fileNames.append(file.getOriginalFilename());
        // Files.write(fileNameAndPath, file.getBytes());
        return null;
    }
}
