package partyround.unit.types.transactions;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Counterparty {
  public abstract String getRoutingNumber();

  public abstract String getAccountNumber();

  public abstract String getAccountType(); // Either Checking or Savings. TODO: replace with enum.

  public abstract String getName();

  public static Builder builder() {
    return new AutoValue_Counterparty.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setRoutingNumber(String routingNumber);

    public abstract Builder setAccountNumber(String accountNumber);

    public abstract Builder setAccountType(String accountType);

    public abstract Builder setName(String name);

    public abstract Counterparty build();
  }
}
