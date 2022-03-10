package partyround.unit.types;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Phone {
  public abstract String getCountryCode();

  public abstract String getNumber();

  public static Phone create(String countryCode, String number) {
    return new AutoValue_Phone(countryCode, number);
  }
}
