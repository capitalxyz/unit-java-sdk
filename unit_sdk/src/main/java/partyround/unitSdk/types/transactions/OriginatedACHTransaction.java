package partyround.unit.types.transactions;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import partyround.unit.types.Direction;
import partyround.unit.types.Relationship;

@AutoValue
public abstract class OriginatedACHTransaction {
  public abstract String getId();

  public abstract Instant getCreatedAt();

  public abstract Direction getDirection();

  public abstract Long getAmountInCents();

  public abstract Long getBalanceInCents();

  public abstract String getSummary();

  public abstract String getDescription();

  public abstract Counterparty getCounterparty();

  public abstract Optional<Map<String, String>> getTags();

  public abstract Relationship getAccount();

  public abstract Relationship getCustomer();

  public abstract Relationship getPayment();

  public static Builder builder() {
    return new AutoValue_OriginatedACHTransaction.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setId(String id);

    public abstract Builder setCreatedAt(Instant createdAt);

    public abstract Builder setDirection(Direction direction);

    public abstract Builder setAmountInCents(Long amountInCents);

    public abstract Builder setBalanceInCents(Long balanceInCents);

    public abstract Builder setSummary(String summary);

    public abstract Builder setDescription(String description);

    public abstract Builder setCounterparty(Counterparty counterparty);

    public abstract Builder setTags(@Nullable Map<String, String> tags);

    public abstract Builder setTags(Optional<Map<String, String>> tags);

    public abstract Builder setAccount(Relationship account);

    public abstract Builder setCustomer(Relationship customer);

    public abstract Builder setPayment(Relationship payment);

    public abstract OriginatedACHTransaction build();
  }
}
