package partyround.unit;

import com.google.auto.value.AutoValue;
import java.io.IOException;
import java.net.InetAddress;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import partyround.unit.types.Address;
import partyround.unit.types.Phone;
import partyround.unit.types.UnitResponse;
import partyround.unit.types.applications.Application;

public class Applications {
  @AutoValue
  abstract static class CreateIndividualApplicationRequest {
    public abstract Optional<String> getSsn();

    public abstract Optional<String> getPassport();

    public abstract Optional<String> getNationality();

    public abstract String getFullName();

    public abstract LocalDate getDateOfBirth();

    public abstract Address getAddress();

    public abstract Phone getPhone();

    public abstract String getEmail();

    public abstract Optional<InetAddress> getIp();

    public abstract Optional<Boolean> getIsSoleProprietorship();

    public abstract Optional<String> getEin();

    public abstract Optional<String> getDba();

    public abstract Optional<Map<String, String>> getTags();

    public abstract Optional<String> getIdempotencyKey();

    // public Future<CustomerToken> executeAsyncWith(UnitContext context) {
    //     return context.getHttpClient()
    //             .sendAsync(context.newRequestBuilderForPath("applications")
    //                     .POST(BodyPublishers.ofString(Serializer.toJson(this))).build(),
    // BodyHandlers.ofString())
    //             .thenApply(response -> Serializer.toIndividualApplication(response.body()));
    // }

    public static Builder builder() {
      return new AutoValue_Applications_CreateIndividualApplicationRequest.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
      public abstract Builder setSsn(String ssn);

      public abstract Builder setPassport(String passport);

      public abstract Builder setNationality(String nationality);

      public abstract Builder setFullName(String fullName);

      public abstract Builder setDateOfBirth(LocalDate dateOfBirth);

      public abstract Builder setAddress(Address address);

      public abstract Builder setPhone(Phone phone);

      public abstract Builder setEmail(String email);

      public abstract Builder setIp(InetAddress ip);

      public abstract Builder setIsSoleProprietorship(Boolean isSoleProprietorship);

      public abstract Builder setEin(String ein);

      public abstract Builder setDba(String dba);

      public abstract Builder setTags(Map<String, String> tags);

      public abstract Builder setIdempotencyKey(String idempotencyKey);

      public abstract CreateIndividualApplicationRequest build();
    }
  }

  @AutoValue
  public abstract static class GetApplicationRequest {
    public abstract String getApplicationId();

    public static Builder builder() {
      return new AutoValue_Applications_GetApplicationRequest.Builder();
    }

    public UnitResponse<Application> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath(String.format("applications/%s", getApplicationId()))
              .GET()
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toApplication);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setApplicationId(String applicationId);

      public abstract GetApplicationRequest build();
    }
  }
}
