package rc.holding.houseplants.domain;

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
}
