package partyround.unit.types;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AuthorizedUser {
  public abstract FullName getFullName();

  public abstract String getEmail();

  public abstract Phone getPhone();

  public static AuthorizedUser create(FullName fullName, String email, Phone phone) {
    return new AutoValue_AuthorizedUser(fullName, email, phone);
  }
}
