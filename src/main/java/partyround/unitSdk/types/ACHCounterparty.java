package partyround.unit.types;

import com.google.auto.value.AutoValue;
import java.time.Instant;

@AutoValue
public abstract class ACHCounterparty {
  public abstract String getId();

  public abstract Instant getCreatedAt();

  public abstract String getName();

  public abstract String getRoutingNumber();

  public abstract String getBank();

  public abstract String getAccountNumber();

  public abstract String getAccountType();

  public abstract CounterpartyType getType();

  public abstract CounterpartyPermissions getPermissions();

  public abstract Relationship getCustomer();

  public static ACHCounterparty.Builder builder() {
    return new AutoValue_ACHCounterparty.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setId(String id);

    public abstract Builder setCreatedAt(Instant createdAt);

    public abstract Builder setName(String name);

    public abstract Builder setRoutingNumber(String routingNumber);

    public abstract Builder setBank(String bank);

    public abstract Builder setAccountNumber(String accountNumber);

    public abstract Builder setAccountType(String accountType);

    public abstract Builder setType(CounterpartyType type);

    public abstract Builder setPermissions(CounterpartyPermissions permissions);

    public abstract Builder setCustomer(Relationship customer);

    public abstract ACHCounterparty build();
  }
}
