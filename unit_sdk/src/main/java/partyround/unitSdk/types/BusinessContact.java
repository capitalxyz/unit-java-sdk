package partyround.unit.types;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BusinessContact {
  public abstract FullName getFullName();

  public abstract String getEmail();

  public abstract Phone getPhone();

  public static BusinessContact create(FullName fullName, String email, Phone phone) {
    return new AutoValue_BusinessContact(fullName, email, phone);
  }
}
