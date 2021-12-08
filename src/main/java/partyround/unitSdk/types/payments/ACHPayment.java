package partyround.unit.types.payments;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import partyround.unit.types.Direction;
import partyround.unit.types.Relationship;
import partyround.unit.types.transactions.Counterparty;

@AutoValue
public abstract class ACHPayment {
  public abstract String getId();

  public abstract Counterparty getCounterparty();

  public abstract Optional<String> getAddenda();

  public abstract Optional<LocalDate> getSettlementDate();

  public abstract Instant getCreatedAt();

  public abstract PaymentStatus getStatus();

  public abstract Optional<String> getReason();

  public abstract Direction getDirection();

  public abstract String getDescription();

  public abstract Long getAmountInCents();

  public abstract Optional<Map<String, String>> getTags();

  public abstract Relationship getAccount();

  public abstract Relationship getCustomer();

  public static Builder builder() {
    return new AutoValue_ACHPayment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setId(String id);

    public abstract Builder setCounterparty(Counterparty counterparty);

    public abstract Builder setAddenda(String addenda);

    public abstract Builder setAddenda(Optional<String> addenda);

    public abstract Builder setSettlementDate(LocalDate settlementDate);

    public abstract Builder setSettlementDate(Optional<LocalDate> settlementDate);

    public abstract Builder setCreatedAt(Instant createdAt);

    public abstract Builder setStatus(PaymentStatus paymentStatus);

    public abstract Builder setReason(String reason);

    public abstract Builder setReason(Optional<String> reason);

    public abstract Builder setDirection(Direction direction);

    public abstract Builder setDescription(String description);

    public abstract Builder setAmountInCents(Long amountInCents);

    public abstract Builder setTags(Map<String, String> tags);

    public abstract Builder setTags(Optional<Map<String, String>> tags);

    public abstract Builder setAccount(Relationship account);

    public abstract Builder setCustomer(Relationship customer);

    public abstract ACHPayment build();
  }
}
