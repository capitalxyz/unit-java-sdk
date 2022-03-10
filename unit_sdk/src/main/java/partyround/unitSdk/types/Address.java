package partyround.unit.types;

import com.google.auto.value.AutoValue;
import java.util.Optional;

@AutoValue
public abstract class Address {
  public abstract String getStreet();

  public abstract Optional<String> getStreet2();

  public abstract String getCity();

  public abstract Optional<String> getState();

  public abstract String getPostalCode();

  public abstract String getCountry(); // ISO31661 Alpha-2

  public static Builder builder() {
    return new AutoValue_Address.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setStreet(String street);

    public abstract Builder setStreet2(String street2);

    public abstract Builder setCity(String city);

    public abstract Builder setState(String state);

    public abstract Builder setPostalCode(String postalCode);

    public abstract Builder setCountry(String country);

    public abstract Address build();
  }
}
