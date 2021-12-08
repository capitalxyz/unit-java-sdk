package partyround.unit;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;
import java.util.stream.Collectors;
import partyround.unit.types.Statement;
import partyround.unit.types.UnitResponse;

public class Statements {
  @AutoValue
  public abstract static class ListRequest {
    public abstract Optional<Integer> getLimit();

    public abstract Optional<Integer> getOffset();

    public abstract Optional<String> getAccountId();

    public abstract Optional<String> getCustomerId();

    public static Builder builder() {
      return new AutoValue_Statements_ListRequest.Builder();
    }

    public UnitResponse<ImmutableList<Statement>> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      ImmutableMap.Builder<String, String> queryBuilder = ImmutableMap.builder();
      getLimit().ifPresent(limit -> queryBuilder.put("page[limit]", String.valueOf(limit)));
      getOffset().ifPresent(offset -> queryBuilder.put("page[offset]", String.valueOf(offset)));
      getAccountId().ifPresent(accountId -> queryBuilder.put("filter[accountId]", accountId));
      getCustomerId().ifPresent(customerId -> queryBuilder.put("filter[customerId]", customerId));

      String query =
          queryBuilder.build().entrySet().stream()
              .map(entry -> entry.getKey() + "=" + entry.getValue())
              .collect(Collectors.joining("&"));

      HttpRequest request = context.newRequestBuilderForPath("statements?" + query).GET().build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toStatementList);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setLimit(Integer limit);

      public abstract Builder setOffset(Integer offset);

      public abstract Builder setAccountId(String accountId);

      public abstract Builder setCustomerId(String customerId);

      public abstract ListRequest build();
    }
  }

  @AutoValue
  public abstract static class GetHTMLByIdRequest {
    public abstract String getStatementId();

    public abstract Optional<String> getCustomerId();

    public abstract Optional<String> getLanguage(); // ISO 639-1, "en" or "es"

    public static Builder builder() {
      return new AutoValue_Statements_GetHTMLByIdRequest.Builder();
    }

    public UnitResponse<String> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      ImmutableMap.Builder<String, String> queryBuilder = ImmutableMap.builder();
      getCustomerId().ifPresent(customerId -> queryBuilder.put("filter[customerId]", customerId));

      String query =
          queryBuilder.build().entrySet().stream()
              .map(entry -> entry.getKey() + "=" + entry.getValue())
              .collect(Collectors.joining("&"));

      HttpRequest request =
          context
              .newRequestBuilderForPath(String.format("statements/%s/html", getStatementId()))
              .GET()
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return UnitResponse.ofResponse(response.body());
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setStatementId(String statementId);

      public abstract Builder setCustomerId(String customerId);

      public abstract Builder setLanguage(String language);

      public abstract GetHTMLByIdRequest build();
    }
  }

  @AutoValue
  public abstract static class GetPDFByIdRequest {
    public abstract String getStatementId();

    public abstract Optional<String> getCustomerId();

    public abstract Optional<String> getLanguage(); // ISO 639-1, "en" or "es"

    public static Builder builder() {
      return new AutoValue_Statements_GetPDFByIdRequest.Builder();
    }

    public UnitResponse<byte[]> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      ImmutableMap.Builder<String, String> queryBuilder = ImmutableMap.builder();
      getCustomerId().ifPresent(customerId -> queryBuilder.put("filter[customerId]", customerId));

      String query =
          queryBuilder.build().entrySet().stream()
              .map(entry -> entry.getKey() + "=" + entry.getValue())
              .collect(Collectors.joining("&"));

      HttpRequest request =
          context
              .newRequestBuilderForPath(String.format("statements/%s/pdf", getStatementId()))
              .GET()
              .build();
      HttpResponse<byte[]> response =
          context.getHttpClient().send(request, BodyHandlers.ofByteArray());
      return UnitResponse.ofResponse(response.body());
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setStatementId(String statementId);

      public abstract Builder setCustomerId(String customerId);

      public abstract Builder setLanguage(String language);

      public abstract GetPDFByIdRequest build();
    }
  }
}
