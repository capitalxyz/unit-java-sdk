package partyround.unit;

import com.google.auto.value.AutoValue;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@AutoValue
public abstract class UnitContext {
  protected abstract HttpClient getHttpClient();

  protected abstract String getToken();

  protected abstract String getApiUrl();

  public HttpRequest.Builder newRequestBuilderForPath(String path) {
    return HttpRequest.newBuilder()
        .uri(URI.create(String.format("%s/%s", getApiUrl(), path)))
        .header("Authorization", String.format("Bearer %s", getToken()))
        .header("Content-Type", "application/vnd.api+json");
  }

  public static Builder builder() {
    return new AutoValue_UnitContext.Builder().setHttpClient(HttpClient.newHttpClient());
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setHttpClient(HttpClient httpClient);

    public abstract Builder setToken(String token);

    public abstract Builder setApiUrl(String apiUrl);

    public abstract UnitContext build();
  }
}
