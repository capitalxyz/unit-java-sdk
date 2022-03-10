package partyround.unit.types.transactions;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import partyround.unit.types.Direction;
import partyround.unit.types.Relationship;

@AutoValue
public abstract class BookTransaction {
  public abstract String getId();

  public abstract Instant getCreatedAt();

  public abstract Direction getDirection();

  public abstract Long getAmountInCents();

  public abstract Long getBalanceInCents();

  public abstract String getSummary();

  public abstract Counterparty getCounterparty();

  public abstract Optional<Map<String, String>> getTags();

  public abstract Relationship getAccount();

  public abstract Relationship getCustomer();

  public abstract Optional<List<Relationship>> getCustomers();

  public abstract Relationship getCounterpartyAccount();

  public abstract Relationship getCounterpartyCustomer();

  public abstract Relationship getPayment();

  public static Builder builder() {
    return new AutoValue_BookTransaction.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setId(String id);

    public abstract Builder setCreatedAt(Instant createdAt);

    public abstract Builder setDirection(Direction direction);

    public abstract Builder setAmountInCents(Long amountInCents);

    public abstract Builder setBalanceInCents(Long balanceInCents);

    public abstract Builder setSummary(String summary);

    public abstract Builder setCounterparty(Counterparty counterparty);

    public abstract Builder setTags(@Nullable Map<String, String> tags);

    public abstract Builder setTags(Optional<Map<String, String>> tags);

    public abstract Builder setAccount(Relationship account);

    public abstract Builder setCustomer(Relationship customer);

    public abstract Builder setCustomers(@Nullable List<Relationship> customers);

    public abstract Builder setCustomers(Optional<List<Relationship>> customers);

    public abstract Builder setCounterpartyAccount(Relationship counterpartyAccount);

    public abstract Builder setCounterpartyCustomer(Relationship counterpartyCustomer);

    public abstract Builder setPayment(Relationship payment);

    public abstract BookTransaction build();
  }
}
