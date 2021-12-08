package partyround.unit.types;

public enum CounterpartyPermissions {
  CREDIT_ONLY("CreditOnly"),
  CREDIT_AND_DEBIT("CreditAndDebit");

  public final String name;

  CounterpartyPermissions(String name) {
    this.name = name;
  }

  public static CounterpartyPermissions fromString(String input) {
    for (CounterpartyPermissions permission : CounterpartyPermissions.values()) {
      if (permission.name.equals(input)) {
        return permission;
      }
    }
    throw new IllegalArgumentException("No matching CounterpartyType for " + input);
  }
}
