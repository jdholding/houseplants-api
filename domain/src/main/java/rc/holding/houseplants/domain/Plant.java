package rc.holding.houseplants.domain;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class Plant {
    Integer id; 
    Integer trefleId; 
    Integer parentId; 
    String family; 
    String genus; 
    String species;
    String commonName; 
    OffsetDateTime dateCreated; 
}
