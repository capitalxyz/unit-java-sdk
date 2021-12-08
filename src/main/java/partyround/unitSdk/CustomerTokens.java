package partyround.unit;

import com.google.auto.value.AutoValue;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import partyround.unit.types.CustomerToken;
import partyround.unit.types.Phone;
import partyround.unit.types.UnitResponse;
import partyround.unit.types.VerificationToken;

public class CustomerTokens {
  @AutoValue
  abstract static class CreateTokenRequest {
    public abstract String getCustomerId();

    public abstract List<String> getScopes();

    public abstract Optional<String> getVerificationToken();

    public abstract Optional<String> getVerificationCode();

    public abstract Optional<Duration> getExpiresIn();

    public UnitResponse<CustomerToken> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath(String.format("customers/%s/token", getCustomerId()))
              .POST(BodyPublishers.ofString(Serializer.toJson(this)))
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toCustomerToken);
    }

    public Builder builder() {
      return new AutoValue_CustomerTokens_CreateTokenRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setCustomerId(String customerId);

      public abstract Builder setScopes(List<String> scopes);

      public abstract Builder setVerificationToken(String verificationToken);

      public abstract Builder setVerificationCode(String verificationCode);

      public abstract Builder setExpiresIn(Duration expiresIn);

      public abstract CreateTokenRequest build();
    }
  }

  public enum Channel {
    SMS("sms"),
    CALL("call");

    public final String value;

    Channel(String value) {
      this.value = value;
    }
  }

  @AutoValue
  abstract static class CreateTokenVerificationRequest {
    public abstract String getCustomerId();

    public abstract Channel getChannel();

    public abstract Optional<Phone> getPhone();

    public UnitResponse<VerificationToken> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath(
                  String.format("customers/%s/token/verification", getCustomerId()))
              .POST(BodyPublishers.ofString(Serializer.toJson(this)))
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toVerificationToken);
    }

    public Builder builder() {
      return new AutoValue_CustomerTokens_CreateTokenVerificationRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setCustomerId(String customerId);

      public abstract Builder setChannel(Channel channel);

      public abstract Builder setPhone(Phone phone);

      public abstract CreateTokenVerificationRequest build();
    }
  }
}
