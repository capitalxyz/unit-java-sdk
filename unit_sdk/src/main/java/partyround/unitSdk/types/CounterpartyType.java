package partyround.unit.types;

public enum CounterpartyType {
  BUSINESS("Business"),
  PERSON("Person"),
  UNKNOWN("Unknown");

  public final String name;

  CounterpartyType(String name) {
    this.name = name;
  }

  public static CounterpartyType fromString(String input) {
    for (CounterpartyType type : CounterpartyType.values()) {
      if (type.name.equals(input)) {
        return type;
      }
    }
    throw new IllegalArgumentException("No matching CounterpartyType for " + input);
  }
}
