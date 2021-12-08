package partyround.unit.types.payments;

public enum PaymentStatus {
  PENDING("Pending"),
  REJECTED("Rejected"),
  CLEARING("Clearing"),
  SENT("Sent"),
  CANCELED("Canceled"),
  RETURNED("Returned");

  final String name;

  PaymentStatus(String name) {
    this.name = name;
  }

  public static PaymentStatus fromString(String input) {
    for (PaymentStatus paymentStatus : PaymentStatus.values()) {
      if (paymentStatus.name.equals(input)) {
        return paymentStatus;
      }
    }
    throw new IllegalArgumentException("No matching PaymentStatus for " + input);
  }
}
