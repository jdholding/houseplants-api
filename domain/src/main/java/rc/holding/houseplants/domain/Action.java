package rc.holding.houseplants.domain;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Action {
    Integer id;
    String type;
    Integer plantId;
    Integer userId;
    OffsetDateTime dateCreated;

}
