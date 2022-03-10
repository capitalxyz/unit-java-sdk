package partyround.unit.types;

public enum Status {
  APPROVED("Approved"),
  DENIED("Denied"),
  PENDING_REVIEW("PendingReview");

  final String status;

  Status(String status) {
    this.status = status;
  }
}
