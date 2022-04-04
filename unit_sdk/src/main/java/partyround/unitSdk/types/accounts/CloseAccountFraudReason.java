package partyround.unit.types.accounts;

public enum CloseAccountFraudReason {
  ACH_ACTIVITY("ACHActivity"),
  CARD_ACTIVITY("CardActivity"),
  CHECK_ACTIVITY("CheckActivity"),
  APPLICATION_HISTORY("ApplicationHistory"),
  ACCOUNT_ACTIVITY("AccountActivity"),
  CLIENT_IDENTIFIED("ClientIdentified");

  public String reason;

  CloseAccountFraudReason(String reason) {
    this.reason = reason;
  }

  public static CloseAccountFraudReason fromString(String input) {
    for (CloseAccountFraudReason reason : CloseAccountFraudReason.values()) {
      if (reason.reason.equals(input)) {
        return reason;
      }
    }
    throw new IllegalArgumentException("No matching CloseAccountFraudReason for " + input);
  }
}
