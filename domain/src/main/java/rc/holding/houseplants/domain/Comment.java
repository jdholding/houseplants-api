package rc.holding.houseplants.domain;

import lombok.Data;

@Data
public class Comment {
    Integer id; 
    Integer userId; 
    Integer plantId; 
    String text; 
    Boolean isHidden; 
}
