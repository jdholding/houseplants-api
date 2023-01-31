package rc.holding.houseplants.domain.search.tools;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InvalidSortFieldException extends RuntimeException{
    
    @Getter private String fieldname; 
}
