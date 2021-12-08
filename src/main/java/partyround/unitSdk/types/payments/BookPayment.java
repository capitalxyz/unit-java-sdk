package partyround.unit.types.payments;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import partyround.unit.types.Direction;
import partyround.unit.types.Relationship;

@AutoValue
public abstract class BookPayment {
  public abstract String getId();

  public abstract Instant getCreatedAt();

  public abstract PaymentStatus getStatus();

  public abstract Optional<String> getReason();

  public abstract Direction getDirection();

  public abstract String getDescription();

  public abstract Long getAmountInCents();

  public abstract Optional<Map<String, String>> getTags();

  public abstract Relationship getAccount();

  public abstract Optional<Relationship> getCustomer();

  public abstract Optional<List<Relationship>> getCustomers();

  public abstract Relationship getCounterpartyAccount();

  public abstract Relationship getCounterpartyCustomer();

  public abstract Relationship getTransaction();

  public static Builder builder() {
    return new AutoValue_BookPayment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setId(String id);

    public abstract Builder setCreatedAt(Instant createdAt);

    public abstract Builder setStatus(PaymentStatus status);

    public abstract Builder setReason(String reason);

    public abstract Builder setReason(Optional<String> reason);

    public abstract Builder setDirection(Direction direction);

    public abstract Builder setDescription(String description);

    public abstract Builder setAmountInCents(Long amountInCents);

    public abstract Builder setTags(Map<String, String> tags);

    public abstract Builder setTags(Optional<Map<String, String>> tags);

    public abstract Builder setAccount(Relationship account);

    public abstract Builder setCustomer(Relationship customer);

    public abstract Builder setCustomer(Optional<Relationship> customer);

    public abstract Builder setCustomers(List<Relationship> customers);

    public abstract Builder setCustomers(Optional<List<Relationship>> customers);

    public abstract Builder setCounterpartyAccount(Relationship counterpartyAccount);

    public abstract Builder setCounterpartyCustomer(Relationship counterpartyCustomer);

    public abstract Builder setTransaction(Relationship transaction);

    public abstract BookPayment build();
  }
}
