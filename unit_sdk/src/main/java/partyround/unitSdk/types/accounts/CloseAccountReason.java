package partyround.unit.types.accounts;

public enum CloseAccountReason {
  BY_CUSTOMER("ByCustomer"),
  FRAUD("Fraud");

  public String reason;

  CloseAccountReason(String reason) {
    this.reason = reason;
  }

  public static CloseAccountReason fromString(String input) {
    for (CloseAccountReason reason : CloseAccountReason.values()) {
      if (reason.reason.equals(input)) {
        return reason;
      }
    }
    throw new IllegalArgumentException("No matching CloseAccountReason for " + input);
  }
}
