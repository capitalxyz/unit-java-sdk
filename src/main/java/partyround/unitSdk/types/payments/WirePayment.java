package partyround.unit.types.payments;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import partyround.unit.types.Direction;
import partyround.unit.types.Relationship;

@AutoValue
public abstract class WirePayment {
  public abstract String getId();

  public abstract Instant getCreatedAt();

  public abstract PaymentStatus getStatus();

  public abstract Optional<String> getReason();

  public abstract Direction getDirection();

  public abstract String getDescription();

  public abstract WireCounterparty getCounterparty();

  public abstract Long getAmountInCents();

  public abstract Optional<Map<String, String>> getTags();

  public abstract Relationship getAccount();

  public abstract Relationship getCustomer();

  public abstract Optional<Relationship> getTransaction();

  public static Builder builder() {
    return new AutoValue_WirePayment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setId(String id);

    public abstract Builder setCreatedAt(Instant createdAt);

    public abstract Builder setStatus(PaymentStatus status);

    public abstract Builder setReason(@Nullable String reason);

    public abstract Builder setReason(Optional<String> reason);

    public abstract Builder setDirection(Direction direction);

    public abstract Builder setDescription(String description);

    public abstract Builder setCounterparty(WireCounterparty counterparty);

    public abstract Builder setAmountInCents(Long amountInCents);

    public abstract Builder setTags(@Nullable Map<String, String> tags);

    public abstract Builder setTags(Optional<Map<String, String>> tags);

    public abstract Builder setAccount(Relationship account);

    public abstract Builder setCustomer(Relationship customer);

    public abstract Builder setTransaction(Optional<Relationship> transaction);

    public abstract WirePayment build();
  }
}
