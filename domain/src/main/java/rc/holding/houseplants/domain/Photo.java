package rc.holding.houseplants.domain;

import lombok.Data;

@Data
public class Photo {
    Integer id; 
    Integer plantId;
    String fileUri; 
}
