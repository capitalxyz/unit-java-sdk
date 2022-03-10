package partyround.unit.types;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FullName {
  public abstract String getFirst();

  public abstract String getLast();

  public static FullName create(String first, String last) {
    return new AutoValue_FullName(first, last);
  }
}
