package partyround.unit.types.accounts;

public enum AccountStatus {
  OPEN("Open"),
  CLOSED("Closed");

  final String status;

  AccountStatus(String status) {
    this.status = status;
  }

  public static AccountStatus fromString(String input) {
    for (AccountStatus accountStatus : AccountStatus.values()) {
      if (accountStatus.status.equals(input)) {
        return accountStatus;
      }
    }
    throw new IllegalArgumentException("No matching AccountStatus for " + input);
  }
}
