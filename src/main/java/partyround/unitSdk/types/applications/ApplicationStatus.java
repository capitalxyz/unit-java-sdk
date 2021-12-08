package partyround.unit.types.applications;

public enum ApplicationStatus {
  AWAITING_DOCUMENTS("AwaitingDocuments"),
  PENDING_REVIEW("PendingReview"),
  PENDING("Pending"),
  APPROVED("Approved"),
  DENIED("Denied");

  final String value;

  ApplicationStatus(String value) {
    this.value = value;
  }
}
