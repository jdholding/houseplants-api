package rc.holding.houseplants.domain;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class Comment {
    Integer id; 
    Integer userId; 
    Integer plantId;
    OffsetDateTime dateCreated;  
    String text; 
    Boolean isHidden; 
}
