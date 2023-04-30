package rc.holding.houseplants.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

  @Getter private Object resourceId;

  private ResourceNotFoundException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage() != null
        ? super.getMessage()
        : getResourceId() == null
            ? "Resource not found"
            : String.format("Resource [%s] not found", getResourceId());
  }

  /** Static constructor used to directly set the message on the exception */
  public static ResourceNotFoundException ofMessage(String message) {
    return new ResourceNotFoundException(message);
  }
}
