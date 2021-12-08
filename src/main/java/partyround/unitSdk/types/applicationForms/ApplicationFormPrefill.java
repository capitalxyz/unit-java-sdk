package partyround.unit.types.applicationForms;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;
import java.util.Optional;
import partyround.unit.types.Address;
import partyround.unit.types.FullName;
import partyround.unit.types.Phone;

@AutoValue
public abstract class ApplicationFormPrefill {
  public abstract ApplicationType getApplicationType();

  public abstract Optional<FullName> getFullName();

  public abstract Optional<String> getSsn();

  public abstract Optional<String> getPassport();

  public abstract Optional<String> getNationality();

  public abstract Optional<LocalDate> getDateOfBirth();

  public abstract Optional<String> getEmail();

  public abstract Optional<String> getName();

  public abstract Optional<String> getStateOfIncorporation();

  public abstract Optional<String> getEntityType();

  // contact, officer, beneficial owners not implemented.

  public abstract Optional<String> getWebsite();

  public abstract Optional<String> getDba();

  public abstract Optional<String> getEin();

  public abstract Optional<Address> getAddress();

  public abstract Optional<Phone> getPhone();

  public static Builder builder() {
    return new AutoValue_ApplicationFormPrefill.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setApplicationType(ApplicationType applicationType);

    public abstract Builder setFullName(FullName fullName);

    public abstract Builder setSsn(String ssn);

    public abstract Builder setPassport(String passport);

    public abstract Builder setNationality(String nationality);

    public abstract Builder setDateOfBirth(LocalDate dateOfBirth);

    public abstract Builder setEmail(String email);

    public abstract Builder setName(String name);

    public abstract Builder setStateOfIncorporation(String stateOfIncorporation);

    public abstract Builder setEntityType(String entityType);

    public abstract Builder setWebsite(String website);

    public abstract Builder setDba(String dba);

    public abstract Builder setEin(String ein);

    public abstract Builder setAddress(Address address);

    public abstract Builder setPhone(Phone phone);

    public abstract ApplicationFormPrefill build();
  }
}
