package partyround.unit;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.Optional;
import partyround.unit.types.Relationship;
import partyround.unit.types.UnitResponse;
import partyround.unit.types.accounts.CloseAccountFraudReason;
import partyround.unit.types.accounts.CloseAccountReason;
import partyround.unit.types.accounts.DepositAccount;

public class Accounts {
  @AutoValue
  public abstract static class CreateDepositAccountRequest {
    public abstract String getDepositProduct();

    public abstract Optional<Map<String, String>> getTags();

    public abstract Optional<String> getIdempotencyKey();

    public abstract Relationship getCustomer();

    public static Builder builder() {
      return new AutoValue_Accounts_CreateDepositAccountRequest.Builder();
    }

    public UnitResponse<DepositAccount> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      Preconditions.checkState(getIdempotencyKey().isPresent(), "Idempotency key is required");
      return executeWithoutIdempotencyWith(context);
    }

    public UnitResponse<DepositAccount> executeWithoutIdempotencyWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath("accounts")
              .POST(BodyPublishers.ofString(Serializer.toJson(this)))
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toDepositAccount);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setDepositProduct(String depositProduct);

      public abstract Builder setTags(Map<String, String> tags);

      public abstract Builder setIdempotencyKey(String idempotencyKey);

      public abstract Builder setCustomer(Relationship customer);

      public abstract CreateDepositAccountRequest build();
    }
  }

  @AutoValue
  public abstract static class CloseDepositAccountRequest {
    public abstract String getId();

    public abstract Optional<CloseAccountReason> getReason();

    public abstract Optional<CloseAccountFraudReason> getFraudReason();

    public static Builder builder() {
      return new AutoValue_Accounts_CloseDepositAccountRequest.Builder();
    }

    public UnitResponse<DepositAccount> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath(String.format("accounts/%s/close", getId()))
              .GET()
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toDepositAccount);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setId(String id);

      public abstract Builder setReason(CloseAccountReason reason);

      public abstract Builder setFraudReason(CloseAccountFraudReason fraudReason);

      public abstract CloseDepositAccountRequest build();
    }
  }

  @AutoValue
  public abstract static class GetByIdRequest {
    public abstract String getId();

    public UnitResponse<DepositAccount> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context.newRequestBuilderForPath(String.format("accounts/%s", getId())).GET().build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toDepositAccount);
    }

    public static Builder builder() {
      return new AutoValue_Accounts_GetByIdRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setId(String id);

      public abstract GetByIdRequest build();
    }
  }
}
