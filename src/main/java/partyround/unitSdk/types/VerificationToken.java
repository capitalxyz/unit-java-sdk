package partyround.unit.types;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VerificationToken {
  public abstract String verificationToken();

  public static VerificationToken create(String verificationToken) {
    return new AutoValue_VerificationToken(verificationToken);
  }
}
