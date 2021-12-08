package partyround.unit.types.payments;

import com.google.auto.value.AutoValue;
import partyround.unit.types.Address;

@AutoValue
public abstract class WireCounterparty {
  public abstract String getRoutingNumber();

  public abstract String getAccountNumber();

  public abstract String getName();

  public abstract Address getAddress();

  public static Builder builder() {
    return new AutoValue_WireCounterparty.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setRoutingNumber(String routingNumber);

    public abstract Builder setAccountNumber(String accountNumber);

    public abstract Builder setName(String name);

    public abstract Builder setAddress(Address address);

    public abstract WireCounterparty build();
  }
}
