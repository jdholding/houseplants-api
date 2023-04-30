package rc.holding.houseplants.domain;

import lombok.Data;

@Data
public class User {
  Integer id;
  String firstName;
  String lastName;
  String email;
  String username;
}
