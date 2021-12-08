package partyround.unit.types;

import com.google.auto.value.AutoValue;
import java.time.Duration;

@AutoValue
public abstract class CustomerToken {
  public abstract String token();

  public abstract Duration expiresIn();

  public static CustomerToken create(String token, Duration expiresIn) {
    return new AutoValue_CustomerToken(token, expiresIn);
  }
}
