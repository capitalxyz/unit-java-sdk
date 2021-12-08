package partyround.unit;

import com.google.auto.value.AutoValue;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import partyround.unit.types.*;

public class ACHCounterparties {
  @AutoValue
  public abstract static class CreateACHCounterpartyRequest {
    public abstract String getName();

    public abstract String getPlaidProcessorToken();

    public abstract CounterpartyType getType();

    public abstract String getCustomerId();

    public UnitResponse<ACHCounterparty> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath("counterparties")
              .POST(BodyPublishers.ofString(Serializer.toJson(this)))
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body())
          .map(Serializer::toCreateACHCounterpartyResponse);
    }

    public static Builder builder() {
      return new AutoValue_ACHCounterparties_CreateACHCounterpartyRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setName(String name);

      public abstract Builder setType(CounterpartyType type);

      public abstract Builder setPlaidProcessorToken(String plaidProcessorToken);

      public abstract Builder setCustomerId(String customerId);

      public abstract CreateACHCounterpartyRequest build();
    }
  }
}
