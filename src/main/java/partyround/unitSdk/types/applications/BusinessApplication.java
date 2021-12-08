package partyround.unit.types.applications;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import partyround.unit.types.Address;
import partyround.unit.types.Phone;
import partyround.unit.types.Relationship;

@AutoValue
public abstract class BusinessApplication {
  public abstract ApplicationStatus getApplicationStatus();

  public abstract String getMessage();

  public abstract Instant getCreatedAt();

  public abstract String getName();

  public abstract Optional<String> getDba();

  public abstract Address getAddress();

  public abstract Phone getPhone();

  public abstract String getStateOfIncorporation(); // TODO: this should be an enum.

  public abstract String getEin();

  public abstract String
      getEntityType(); // Corporation, LLC, or Partnerhsip. TODO: this should be an enum.

  public abstract Optional<Map<String, String>> getTags();

  public abstract Relationship getOrg();

  public abstract List<Relationship> getDocuments();

  public abstract Optional<Relationship> getCustomer();

  public static Builder builder() {
    return new AutoValue_BusinessApplication.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setApplicationStatus(ApplicationStatus applicationStatus);

    public abstract Builder setMessage(String message);

    public abstract Builder setCreatedAt(Instant createdAt);

    public abstract Builder setName(String name);

    public abstract Builder setDba(String dba);

    public abstract Builder setAddress(Address address);

    public abstract Builder setPhone(Phone phone);

    public abstract Builder setStateOfIncorporation(String stateOfIncorporation);

    public abstract Builder setEin(String ein);

    public abstract Builder setEntityType(String entityType);

    public abstract Builder setTags(Map<String, String> tags);

    public abstract Builder setOrg(Relationship org);

    public abstract Builder setDocuments(List<Relationship> documents);

    public abstract Builder setCustomer(Optional<Relationship> customer);

    public abstract BusinessApplication build();
  }
}
