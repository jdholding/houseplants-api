package rc.holding.houseplants.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
  Integer id;
  String firstName;
  String lastName;
  String email;
  String username;
}
