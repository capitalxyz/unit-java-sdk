package partyround.unit.types;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Relationship {
  public abstract String getType();

  public abstract String getId();

  public static Relationship create(String type, String id) {
    return new AutoValue_Relationship(type, id);
  }
}
