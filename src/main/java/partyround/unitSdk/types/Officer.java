package partyround.unit.types;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;
import java.util.Optional;

@AutoValue
public abstract class Officer {
  public abstract Optional<Status> getStatus();

  public abstract FullName getFullName();

  public abstract Optional<String> getTitle();

  public abstract Optional<String> getSsn();

  public abstract Optional<String> getPassport();

  public abstract Optional<String> getNationality(); // ISO31661 - Alpha2

  public abstract LocalDate getDateOfBirth();

  public abstract Address getAddress();

  public abstract Phone getPhone();

  public abstract String getEmail();

  public static Builder builder() {
    return new AutoValue_Officer.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setStatus(Status status);

    public abstract Builder setFullName(FullName fullName);

    public abstract Builder setTitle(String title);

    public abstract Builder setSsn(String ssn);

    public abstract Builder setPassport(String passport);

    public abstract Builder setNationality(String nationality);

    public abstract Builder setDateOfBirth(LocalDate dateOfBirth);

    public abstract Builder setAddress(Address address);

    public abstract Builder setPhone(Phone phone);

    public abstract Builder setEmail(String email);

    public abstract Officer build();
  }
}
