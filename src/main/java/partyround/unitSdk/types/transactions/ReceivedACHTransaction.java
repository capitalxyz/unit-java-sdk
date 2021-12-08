package partyround.unit.types.transactions;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import partyround.unit.types.Direction;
import partyround.unit.types.Relationship;

@AutoValue
public abstract class ReceivedACHTransaction {
  public abstract String getId();

  public abstract Instant getCreatedAt();

  public abstract Direction getDirection();

  public abstract Long getAmountInCents();

  public abstract Long getBalanceInCents();

  public abstract String getSummary();

  public abstract String getDescription();

  public abstract Optional<String> getAddenda();

  public abstract String getCompanyName();

  public abstract String getCounterpartyRoutingNumber();

  public abstract Optional<String> getTraceNumber();

  public abstract Optional<String> getSECCode();

  public abstract Optional<Map<String, String>> getTags();

  public abstract Relationship getAccount();

  public abstract Relationship getCustomer();

  public static Builder builder() {
    return new AutoValue_ReceivedACHTransaction.Builder();
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

    public abstract Builder setAddenda(String addenda);

    public abstract Builder setAddenda(Optional<String> addenda);

    public abstract Builder setCompanyName(String companyName);

    public abstract Builder setCounterpartyRoutingNumber(String counterpartyRoutingNumber);

    public abstract Builder setTraceNumber(Optional<String> traceNumber);

    public abstract Builder setSECCode(String secCode);

    public abstract Builder setSECCode(Optional<String> secCode);

    public abstract Builder setTags(Map<String, String> tags);

    public abstract Builder setTags(Optional<Map<String, String>> tags);

    public abstract Builder setAccount(Relationship account);

    public abstract Builder setCustomer(Relationship customer);

    public abstract ReceivedACHTransaction build();
  }
}
