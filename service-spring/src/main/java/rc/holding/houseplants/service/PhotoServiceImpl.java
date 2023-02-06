package rc.holding.houseplants.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Random;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.repository.api.PhotoRepository;
import rc.holding.houseplants.service.api.PhotoService;

@Service
public class PhotoServiceImpl implements PhotoService {

    public PhotoServiceImpl(PhotoRepository repo) {
        this.repo = repo; 
    }
    
    private final PhotoRepository repo;
    
    @Value("${app.url.context}")
    public String contextPath;

    @Value("${app.photos.original-path}")
    public String originalDir; 

    @Value("${app.photos.thumbnail-path}")
    public String thumbnailDir; 

    @Override
    public Photo uploadPhoto(MultipartFile file, Photo photo) throws IOException {

        String imageUri = "/images/";
        String thumbnailUri = "/images/thumbnails/";

        BufferedImage buffImage = ImageIO.read(file.getInputStream());
        var fullImage = preparePhoto(buffImage);
        var thumbnail = prepareThumbnail(buffImage);   
        Random random = new Random(); 
        // StringBuilder fileNames = new StringBuilder();
        String filename = photo.getPlantId()+ "-"+ random.nextInt(100000,999999) +".jpg"; 
        // Path fileNameAndPath = Paths.get(originalDir, newFilename);
        writeFile(originalDir, filename, fullImage);
        writeFile(thumbnailDir, filename, thumbnail); 
        // fileNames.append(file.getOriginalFilename());
        // Files.write(fileNameAndPath, toByteArray(imageFull));
        photo.setPhotoUri(imageUri + filename);
        photo.setThumbnailUri(thumbnailUri + filename);
        repo.insert(photo); 
        return null;
    }

    public void writeFile(String dir, String filename, BufferedImage image) throws IOException {
        Path filePath = Paths.get(dir, filename); 
        Files.write(filePath, toByteArray(image)); 
    }

    public BufferedImage preparePhoto(BufferedImage image) {
        return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 1000);
    }

    public BufferedImage prepareThumbnail(BufferedImage image) {
        return Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.AUTOMATIC, 200); 
    }

    public byte[] toByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray(); 
    }
}
