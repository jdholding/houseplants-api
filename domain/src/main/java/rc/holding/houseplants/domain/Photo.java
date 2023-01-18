package rc.holding.houseplants.domain;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class Photo {
    Integer id; 
    Integer plantId;
    String fileUri;
    OffsetDateTime dateCreated; 
}
