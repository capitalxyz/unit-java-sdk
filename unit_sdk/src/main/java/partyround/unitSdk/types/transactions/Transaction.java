package partyround.unit.types.transactions;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import partyround.unit.types.Direction;

/**
 * If you are wondering why an enum + autovalue instead of an interface to inherit from, this
 * approach is chosen because it allows exhaustive switches to be implemented. Since in many
 * applications unknown transaction types are error-prone, having an exhaustive switch is a good
 * idea.
 */
@AutoValue
public abstract class Transaction {
  public enum TransactionType {
    ADJUSTMENT_TRANSACTION("adjustmentTransaction"),
    BOOK_TRANSACTION("bookTransaction"),
    DISHONORED_ACH_TRANSACTION("dishonoredAchTransaction"),
    FEE_TRANSACTION("feeTransaction"),
    INTEREST_TRANSACTION("interestTransaction"),
    ORIGINATED_ACH_TRANSACTION("originatedAchTransaction"),
    RECEIVED_ACH_TRANSACTION("receivedAchTransaction"),
    RETURNED_ACH_TRANSACTION("returnedAchTransaction"),
    RETURNED_RECEIVED_ACH_TRANSACTION("returnedReceivedAchTransaction"),
    WIRE_TRANSACTION("wireTransaction");

    String value;

    TransactionType(String v) {
      value = v;
    }

    public static Optional<TransactionType> fromString(String s) {
      return Arrays.stream(TransactionType.values())
          .filter(type -> s.equals(type.value))
          .findFirst();
    }
  }

  public abstract TransactionType getType();

  abstract Optional<AdjustmentTransaction> getAdjustmentTransactionOptional();

  public AdjustmentTransaction getAdjustmentTransaction() {
    return getAdjustmentTransactionOptional()
        .orElseThrow(
            () -> new IllegalStateException("Transaction is not an AdjustmentTransaction"));
  }

  abstract Optional<DishonoredACHTransaction> getDishonoredACHTransactionOptional();

  public DishonoredACHTransaction getDishonoredACHTransaction() {
    return getDishonoredACHTransactionOptional()
        .orElseThrow(
            () -> new IllegalStateException("Transaction is not a DishonoredACHTransaction"));
  }

  abstract Optional<FeeTransaction> getFeeTransactionOptional();

  public FeeTransaction getFeeTransaction() {
    return getFeeTransactionOptional()
        .orElseThrow(() -> new IllegalStateException("Transaction is not a FeeTransaction"));
  }

  abstract Optional<InterestTransaction> getInterestTransactionOptional();

  public InterestTransaction getInterestTransaction() {
    return getInterestTransactionOptional()
        .orElseThrow(() -> new IllegalStateException("Transaction is not an InterestTransaction"));
  }

  abstract Optional<OriginatedACHTransaction> getOriginatedACHTransactionOptional();

  public OriginatedACHTransaction getOriginatedACHTransaction() {
    return getOriginatedACHTransactionOptional()
        .orElseThrow(
            () -> new IllegalStateException("Transaction is not an OriginatedACHTransaction"));
  }

  abstract Optional<ReceivedACHTransaction> getReceivedACHTransactionOptional();

  public ReceivedACHTransaction getReceivedACHTransaction() {
    return getReceivedACHTransactionOptional()
        .orElseThrow(
            () -> new IllegalStateException("Transaction is not a ReceivedACHTransaction"));
  }

  abstract Optional<ReturnedACHTransaction> getReturnedACHTransactionOptional();

  public ReturnedACHTransaction getReturnedACHTransaction() {
    return getReturnedACHTransactionOptional()
        .orElseThrow(
            () -> new IllegalStateException("Transaction is not a ReturnedACHTransaction"));
  }

  abstract Optional<ReturnedReceivedACHTransaction> getReturnedReceivedACHTransactionOptional();

  public ReturnedReceivedACHTransaction getReturnedReceivedACHTransaction() {
    return getReturnedReceivedACHTransactionOptional()
        .orElseThrow(
            () -> new IllegalStateException("Transaction is not a ReturnedReceivedACHTransaction"));
  }

  abstract Optional<WireTransaction> getWireTransactionOptional();

  public WireTransaction getWireTransaction() {
    return getWireTransactionOptional()
        .orElseThrow(() -> new IllegalStateException("Transaction is not a WireTransaction"));
  }

  abstract Optional<BookTransaction> getBookTransactionOptional();

  public BookTransaction getBookTransaction() {
    return getBookTransactionOptional()
        .orElseThrow(() -> new IllegalStateException("Transaction is not a BookTransaction"));
  }

  public String getId() {
    switch (getType()) {
      case ADJUSTMENT_TRANSACTION:
        return getAdjustmentTransaction().getId();
      case DISHONORED_ACH_TRANSACTION:
        return getDishonoredACHTransaction().getId();
      case FEE_TRANSACTION:
        return getFeeTransaction().getId();
      case INTEREST_TRANSACTION:
        return getInterestTransaction().getId();
      case ORIGINATED_ACH_TRANSACTION:
        return getOriginatedACHTransaction().getId();
      case RECEIVED_ACH_TRANSACTION:
        return getReceivedACHTransaction().getId();
      case RETURNED_ACH_TRANSACTION:
        return getReturnedACHTransaction().getId();
      case RETURNED_RECEIVED_ACH_TRANSACTION:
        return getReturnedReceivedACHTransaction().getId();
      case WIRE_TRANSACTION:
        return getWireTransaction().getId();
      case BOOK_TRANSACTION:
        return getBookTransaction().getId();
    }
    throw new IllegalStateException("Transaction is not a known type");
  }

  public Instant getCreatedAt() {
    switch (getType()) {
      case ADJUSTMENT_TRANSACTION:
        return getAdjustmentTransaction().getCreatedAt();
      case DISHONORED_ACH_TRANSACTION:
        return getDishonoredACHTransaction().getCreatedAt();
      case FEE_TRANSACTION:
        return getFeeTransaction().getCreatedAt();
      case INTEREST_TRANSACTION:
        return getInterestTransaction().getCreatedAt();
      case ORIGINATED_ACH_TRANSACTION:
        return getOriginatedACHTransaction().getCreatedAt();
      case RECEIVED_ACH_TRANSACTION:
        return getReceivedACHTransaction().getCreatedAt();
      case RETURNED_ACH_TRANSACTION:
        return getReturnedACHTransaction().getCreatedAt();
      case RETURNED_RECEIVED_ACH_TRANSACTION:
        return getReturnedReceivedACHTransaction().getCreatedAt();
      case WIRE_TRANSACTION:
        return getWireTransaction().getCreatedAt();
      case BOOK_TRANSACTION:
        return getBookTransaction().getCreatedAt();
    }
    throw new IllegalStateException("Transaction is not a known type");
  }

  public Direction getDirection() {
    switch (getType()) {
      case ADJUSTMENT_TRANSACTION:
        return getAdjustmentTransaction().getDirection();
      case DISHONORED_ACH_TRANSACTION:
        return getDishonoredACHTransaction().getDirection();
      case FEE_TRANSACTION:
        return getFeeTransaction().getDirection();
      case INTEREST_TRANSACTION:
        return getInterestTransaction().getDirection();
      case ORIGINATED_ACH_TRANSACTION:
        return getOriginatedACHTransaction().getDirection();
      case RECEIVED_ACH_TRANSACTION:
        return getReceivedACHTransaction().getDirection();
      case RETURNED_ACH_TRANSACTION:
        return getReturnedACHTransaction().getDirection();
      case RETURNED_RECEIVED_ACH_TRANSACTION:
        return getReturnedReceivedACHTransaction().getDirection();
      case WIRE_TRANSACTION:
        return getWireTransaction().getDirection();
      case BOOK_TRANSACTION:
        return getBookTransaction().getDirection();
    }
    throw new IllegalStateException("Transaction is not a known type");
  }

  public long getAmountInCents() {
    switch (getType()) {
      case ADJUSTMENT_TRANSACTION:
        return getAdjustmentTransaction().getAmountInCents();
      case DISHONORED_ACH_TRANSACTION:
        return getDishonoredACHTransaction().getAmountInCents();
      case FEE_TRANSACTION:
        return getFeeTransaction().getAmountInCents();
      case INTEREST_TRANSACTION:
        return getInterestTransaction().getAmountInCents();
      case ORIGINATED_ACH_TRANSACTION:
        return getOriginatedACHTransaction().getAmountInCents();
      case RECEIVED_ACH_TRANSACTION:
        return getReceivedACHTransaction().getAmountInCents();
      case RETURNED_ACH_TRANSACTION:
        return getReturnedACHTransaction().getAmountInCents();
      case RETURNED_RECEIVED_ACH_TRANSACTION:
        return getReturnedReceivedACHTransaction().getAmountInCents();
      case WIRE_TRANSACTION:
        return getWireTransaction().getAmountInCents();
      case BOOK_TRANSACTION:
        return getBookTransaction().getAmountInCents();
    }
    throw new IllegalStateException("Transaction is not a known type");
  }

  public long getBalanceInCents() {
    switch (getType()) {
      case ADJUSTMENT_TRANSACTION:
        return getAdjustmentTransaction().getBalanceInCents();
      case DISHONORED_ACH_TRANSACTION:
        return getDishonoredACHTransaction().getBalanceInCents();
      case FEE_TRANSACTION:
        return getFeeTransaction().getBalanceInCents();
      case INTEREST_TRANSACTION:
        return getInterestTransaction().getBalanceInCents();
      case ORIGINATED_ACH_TRANSACTION:
        return getOriginatedACHTransaction().getBalanceInCents();
      case RECEIVED_ACH_TRANSACTION:
        return getReceivedACHTransaction().getBalanceInCents();
      case RETURNED_ACH_TRANSACTION:
        return getReturnedACHTransaction().getBalanceInCents();
      case RETURNED_RECEIVED_ACH_TRANSACTION:
        return getReturnedReceivedACHTransaction().getBalanceInCents();
      case WIRE_TRANSACTION:
        return getWireTransaction().getBalanceInCents();
      case BOOK_TRANSACTION:
        return getBookTransaction().getBalanceInCents();
    }
    throw new IllegalStateException("Transaction is not a known type");
  }

  public String getSummary() {
    switch (getType()) {
      case ADJUSTMENT_TRANSACTION:
        return getAdjustmentTransaction().getSummary();
      case DISHONORED_ACH_TRANSACTION:
        return getDishonoredACHTransaction().getSummary();
      case FEE_TRANSACTION:
        return getFeeTransaction().getSummary();
      case INTEREST_TRANSACTION:
        return getInterestTransaction().getSummary();
      case ORIGINATED_ACH_TRANSACTION:
        return getOriginatedACHTransaction().getSummary();
      case RECEIVED_ACH_TRANSACTION:
        return getReceivedACHTransaction().getSummary();
      case RETURNED_ACH_TRANSACTION:
        return getReturnedACHTransaction().getSummary();
      case RETURNED_RECEIVED_ACH_TRANSACTION:
        return getReturnedReceivedACHTransaction().getSummary();
      case WIRE_TRANSACTION:
        return getWireTransaction().getSummary();
      case BOOK_TRANSACTION:
        return getBookTransaction().getSummary();
    }
    throw new IllegalStateException("Transaction is not a known type");
  }

  public static Builder builder() {
    return new AutoValue_Transaction.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setType(TransactionType type);

    public abstract Builder setAdjustmentTransactionOptional(
        AdjustmentTransaction adjustmentTransaction);

    public abstract Builder setDishonoredACHTransactionOptional(
        DishonoredACHTransaction transaction);

    public abstract Builder setFeeTransactionOptional(FeeTransaction feeTransaction);

    public abstract Builder setInterestTransactionOptional(InterestTransaction interestTransaction);

    public abstract Builder setOriginatedACHTransactionOptional(
        OriginatedACHTransaction transaction);

    public abstract Builder setReceivedACHTransactionOptional(ReceivedACHTransaction transaction);

    public abstract Builder setReturnedACHTransactionOptional(ReturnedACHTransaction transaction);

    public abstract Builder setReturnedReceivedACHTransactionOptional(
        ReturnedReceivedACHTransaction transaction);

    public abstract Builder setWireTransactionOptional(WireTransaction transaction);

    public abstract Builder setBookTransactionOptional(BookTransaction transaction);

    public abstract Transaction build();
  }
}
