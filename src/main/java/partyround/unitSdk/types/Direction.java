package partyround.unit.types;

public enum Direction {
  DEBIT("Debit"),
  CREDIT("Credit");

  public final String value;

  Direction(String value) {
    this.value = value;
  }

  public static Direction fromString(String input) {
    for (Direction direction : Direction.values()) {
      if (direction.value.equals(input)) {
        return direction;
      }
    }
    throw new IllegalArgumentException("No matching Direction for " + input);
  }
}
