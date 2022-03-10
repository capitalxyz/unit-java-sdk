package partyround.unit;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import partyround.unit.types.ACHCounterparty;
import partyround.unit.types.Direction;
import partyround.unit.types.Relationship;
import partyround.unit.types.UnitResponse;
import partyround.unit.types.payments.ACHPayment;
import partyround.unit.types.payments.BookPayment;
import partyround.unit.types.payments.WireCounterparty;
import partyround.unit.types.payments.WirePayment;

public class Payments {
  @AutoValue
  public abstract static class CreateBookPaymentRequest {
    public abstract Long getAmountInCents();

    public abstract String getDescription();

    public abstract Optional<String> getIdempotencyKey();

    public abstract Optional<Map<String, String>> getTags();

    public abstract Relationship getAccount();

    public abstract Relationship getCounterpartyAccount();

    public static Builder builder() {
      return new AutoValue_Payments_CreateBookPaymentRequest.Builder();
    }

    public UnitResponse<BookPayment> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      Preconditions.checkState(getIdempotencyKey().isPresent(), "Idempotency key is required");
      return executeWithoutIdempotencyWith(context);
    }

    public UnitResponse<BookPayment> executeWithoutIdempotencyWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath("payments")
              .POST(HttpRequest.BodyPublishers.ofString(Serializer.toJson(this)))
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toBookPayment);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setAmountInCents(Long amountInCents);

      public abstract Builder setDescription(String description);

      public abstract Builder setIdempotencyKey(@Nullable String idempotencyKey);

      public abstract Builder setIdempotencyKey(Optional<String> idempotencyKey);

      public abstract Builder setTags(Map<String, String> tags);

      public abstract Builder setAccount(Relationship account);

      public abstract Builder setCounterpartyAccount(Relationship counterpartyAccount);

      public abstract CreateBookPaymentRequest build();
    }
  }

  @AutoValue
  public abstract static class CreateInlinePaymentRequest {
    public abstract Long getAmountInCents();

    public abstract Optional<Direction> getDirection();

    public abstract ACHCounterparty getCounterparty();

    public abstract String getDescription();

    public abstract Optional<String> getAddenda();

    public abstract Optional<String> getIdempotencyKey();

    public abstract Optional<Map<String, String>> getTags();

    public abstract Relationship getAccount();

    public static Builder builder() {
      return new AutoValue_Payments_CreateInlinePaymentRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setAmountInCents(Long amountInCents);

      public abstract Builder setDirection(Direction direction);

      public abstract Builder setCounterparty(ACHCounterparty counterparty);

      public abstract Builder setDescription(String description);

      public abstract Builder setAddenda(String addenda);

      public abstract Builder setIdempotencyKey(@Nullable String idempotencyKey);

      public abstract Builder setIdempotencyKey(Optional<String> idempotencyKey);

      public abstract Builder setTags(Map<String, String> tags);

      public abstract Builder setAccount(Relationship account);

      public abstract CreateInlinePaymentRequest build();
    }
  }

  @AutoValue
  public abstract static class CreateLinkedPaymentRequest {
    public abstract Long getAmountInCents();

    public abstract Optional<Direction> getDirection();

    public abstract String getDescription();

    public abstract Optional<String> getAddenda();

    public abstract Optional<String> getIdempotencyKey();

    public abstract Optional<Map<String, String>> getTags();

    public abstract Relationship getAccount();

    public abstract Relationship getCounterpartyAccount();

    public static Builder builder() {
      return new AutoValue_Payments_CreateLinkedPaymentRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setAmountInCents(Long amountInCents);

      public abstract Builder setDirection(Direction direction);

      public abstract Builder setDescription(String description);

      public abstract Builder setAddenda(@Nullable String addenda);

      public abstract Builder setAddenda(Optional<String> addenda);

      public abstract Builder setIdempotencyKey(@Nullable String idempotencyKey);

      public abstract Builder setIdempotencyKey(Optional<String> idempotencyKey);

      public abstract Builder setTags(Map<String, String> tags);

      public abstract Builder setAccount(Relationship account);

      public abstract Builder setCounterpartyAccount(Relationship counterpartyAccount);

      public abstract CreateLinkedPaymentRequest build();
    }
  }

  @AutoValue
  public abstract static class CreateVerifiedPaymentRequest {
    public abstract Long getAmountInCents();

    public abstract Direction getDirection();

    public abstract String getDescription();

    public abstract Optional<String> getAddenda();

    public abstract Optional<String> getIdempotencyKey();

    public abstract Optional<String> getCounterpartyName();

    public abstract String getPlaidProcessorToken();

    public abstract Relationship getAccount();

    public abstract boolean getVerifyCounterpartyBalance();

    public static Builder builder() {
      return new AutoValue_Payments_CreateVerifiedPaymentRequest.Builder();
    }

    public UnitResponse<ACHPayment> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      Preconditions.checkState(getIdempotencyKey().isPresent(), "Idempotency key is required");
      return executeWithoutIdempotencyWith(context);
    }

    public UnitResponse<ACHPayment> executeWithoutIdempotencyWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath("payments")
              .POST(HttpRequest.BodyPublishers.ofString(Serializer.toJson(this)))
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toACHPayment);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setAmountInCents(Long amountInCents);

      public abstract Builder setDirection(Direction direction);

      public abstract Builder setDescription(String description);

      public abstract Builder setAddenda(@Nullable String addenda);

      public abstract Builder setAddenda(Optional<String> addenda);

      public abstract Builder setIdempotencyKey(@Nullable String idempotencyKey);

      public abstract Builder setIdempotencyKey(Optional<String> idempotencyKey);

      public abstract Builder setCounterpartyName(@Nullable String counterpartyName);

      public abstract Builder setCounterpartyName(Optional<String> counterpartyName);

      public abstract Builder setPlaidProcessorToken(String plaidProcessorToken);

      public abstract Builder setAccount(Relationship account);

      public abstract Builder setVerifyCounterpartyBalance(boolean verifyCounterpartyBalance);

      public abstract CreateVerifiedPaymentRequest build();
    }
  }

  @AutoValue
  public abstract static class CreateWirePaymentRequest {
    public abstract Long getAmountInCents();

    public abstract String getDescription();

    public abstract WireCounterparty getCounterparty();

    public abstract Optional<String> getIdempotencyKey();

    public abstract Optional<Map<String, String>> getTags();

    public abstract Relationship getAccount();

    public static Builder builder() {
      return new AutoValue_Payments_CreateWirePaymentRequest.Builder();
    }

    public UnitResponse<WirePayment> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      Preconditions.checkState(getIdempotencyKey().isPresent(), "Idempotency key is required");
      return executeWithoutIdempotencyWith(context);
    }

    public UnitResponse<WirePayment> executeWithoutIdempotencyWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath("payments")
              .POST(HttpRequest.BodyPublishers.ofString(Serializer.toJson(this)))
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toWirePayment);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setAmountInCents(Long amountInCents);

      public abstract Builder setDescription(String description);

      public abstract Builder setCounterparty(WireCounterparty counterparty);

      public abstract Builder setIdempotencyKey(@Nullable String idempotencyKey);

      public abstract Builder setIdempotencyKey(Optional<String> idempotencyKey);

      public abstract Builder setTags(@Nullable Map<String, String> tags);

      public abstract Builder setTags(Optional<Map<String, String>> tags);

      public abstract Builder setAccount(Relationship account);

      public abstract CreateWirePaymentRequest build();
    }
  }
}
