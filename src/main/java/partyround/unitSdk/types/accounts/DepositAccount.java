package partyround.unit.types.accounts;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@AutoValue
public abstract class DepositAccount {
  public abstract String getId();

  public abstract Instant getCreatedAt();

  public abstract String getName();

  public abstract String getDepositProduct();

  public abstract String getRoutingNumber();

  public abstract String getAccountNumber();

  public abstract String getCurrency();

  // from discussion with Unit: balance - hold = available.

  public abstract long getBalanceInCents();

  public abstract long getHoldInCents();

  public abstract long getAvailableInCents();

  public abstract Optional<Map<String, String>> getTags();

  public abstract AccountStatus getStatus();

  public static Builder builder() {
    return new AutoValue_DepositAccount.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setId(String id);

    public abstract Builder setCreatedAt(Instant createdAt);

    public abstract Builder setName(String name);

    public abstract Builder setDepositProduct(String depositProduct);

    public abstract Builder setRoutingNumber(String routingNumber);

    public abstract Builder setAccountNumber(String accountNumber);

    public abstract Builder setCurrency(String currency);

    public abstract Builder setBalanceInCents(long balanceInCents);

    public abstract Builder setHoldInCents(long holdInCents);

    public abstract Builder setAvailableInCents(long availableInCents);

    public abstract Builder setTags(Map<String, String> tags);

    public abstract Builder setTags(Optional<Map<String, String>> tags);

    public abstract Builder setStatus(AccountStatus status);

    public abstract DepositAccount build();
  }
}
