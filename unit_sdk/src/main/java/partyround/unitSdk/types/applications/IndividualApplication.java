package partyround.unit.types.applications;

import com.google.auto.value.AutoValue;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import partyround.unit.types.Address;
import partyround.unit.types.Phone;
import partyround.unit.types.Relationship;

@AutoValue
public abstract class IndividualApplication {
  public abstract ApplicationStatus getApplicationStatus();

  public abstract String getMessage();

  public abstract Instant getCreatedAt();

  public abstract Optional<String> getSsn();

  public abstract Optional<String> getPassport();

  public abstract String getNationality(); // ISO31661 Alpha-2

  public abstract String getFullName();

  public abstract LocalDate getDateOfBirth();

  public abstract Address getAddress();

  public abstract Phone getPhone();

  public abstract String getEmail();

  public abstract Optional<InetAddress> getIp();

  public abstract Optional<Boolean> getIsSoleProprietorship();

  public abstract Optional<String> getEin();

  public abstract Optional<String> getDba();

  public abstract Optional<Map<String, String>> getTags();

  public abstract Relationship getOrg();

  public abstract List<Relationship> getDocuments();

  public abstract Optional<Relationship> getCustomer();

  public static Builder builder() {
    return new AutoValue_IndividualApplication.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setApplicationStatus(ApplicationStatus applicationStatus);

    public abstract Builder setMessage(String message);

    public abstract Builder setCreatedAt(Instant createdAt);

    public abstract Builder setSsn(String ssn);

    public abstract Builder setPassport(String passport);

    public abstract Builder setNationality(String nationality);

    public abstract Builder setFullName(String fullName);

    public abstract Builder setDateOfBirth(LocalDate dateOfBirth);

    public abstract Builder setAddress(Address address);

    public abstract Builder setPhone(Phone phone);

    public abstract Builder setEmail(String email);

    public abstract Builder setIp(InetAddress ip);

    public abstract Builder setIsSoleProprietorship(Boolean isSoleProprietorship);

    public abstract Builder setEin(String ein);

    public abstract Builder setDba(String dba);

    public abstract Builder setTags(Map<String, String> tags);

    public abstract Builder setOrg(Relationship org);

    public abstract Builder setDocuments(List<Relationship> documents);

    public abstract Builder setCustomer(Relationship customer);

    public abstract IndividualApplication build();
  }
}
