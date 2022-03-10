package partyround.unit;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import partyround.unit.types.UnitResponse;
import partyround.unit.types.transactions.Transaction;

public class Transactions {
  @AutoValue
  public abstract static class GetByIdRequest {
    public abstract String getAccountId();

    public abstract String getTransactionId();

    public abstract Optional<String> getCustomerId();

    public static Builder builder() {
      return new AutoValue_Transactions_GetByIdRequest.Builder();
    }

    public UnitResponse<Transaction> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      ImmutableMap.Builder<String, String> queryBuilder = ImmutableMap.builder();
      getCustomerId().ifPresent(customerId -> queryBuilder.put("filter[customerId]", customerId));

      String query =
          queryBuilder.build().entrySet().stream()
              .map(entry -> entry.getKey() + "=" + entry.getValue())
              .collect(Collectors.joining("&"));

      HttpRequest request =
          context
              .newRequestBuilderForPath(
                  String.format(
                      "accounts/%s/transactions/%s?%s", getAccountId(), getTransactionId(), query))
              .GET()
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toTransaction);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setAccountId(String accountId);

      public abstract Builder setTransactionId(String transactionId);

      public abstract Builder setCustomerId(String customerId);

      public abstract GetByIdRequest build();
    }
  }

  @AutoValue
  public abstract static class ListRequest {
    public abstract Optional<Long> getLimit();

    public abstract Optional<Long> getOffset();

    public abstract Optional<String> getAccountId();

    public abstract Optional<String> getCustomerId();

    public abstract Optional<String> getQuery();

    public abstract Optional<Map<String, String>> getTags();

    public abstract Optional<Instant> getSince();

    public abstract Optional<Instant> getUntil();

    public abstract Optional<String> getCardId();

    public abstract Optional<String> getSort();

    public static Builder builder() {
      return new AutoValue_Transactions_ListRequest.Builder();
    }

    public UnitResponse<List<Transaction>> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      ImmutableMap.Builder<String, String> queryBuilder = ImmutableMap.builder();

      getLimit().ifPresent(limit -> queryBuilder.put("filter[limit]", String.valueOf(limit)));
      getOffset().ifPresent(offset -> queryBuilder.put("filter[offset]", String.valueOf(offset)));
      getAccountId().ifPresent(accountId -> queryBuilder.put("filter[accountId]", accountId));
      getCustomerId().ifPresent(customerId -> queryBuilder.put("filter[customerId]", customerId));
      getQuery().ifPresent(query -> queryBuilder.put("filter[query]", query));
      // TODO: add tags
      getSince().ifPresent(since -> queryBuilder.put("filter[since]", since.toString()));
      getUntil().ifPresent(until -> queryBuilder.put("filter[until]", until.toString()));
      getCardId().ifPresent(cardId -> queryBuilder.put("filter[cardId]", cardId));

      String query =
          queryBuilder.build().entrySet().stream()
              .map(entry -> entry.getKey() + "=" + entry.getValue())
              .collect(Collectors.joining("&"));

      HttpRequest request =
          context.newRequestBuilderForPath(String.format("transactions?%s", query)).GET().build();

      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());

      return Serializer.toUnitResponse(response.body()).map(Serializer::toTransactionsList);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setLimit(Long limit);

      public abstract Builder setOffset(Long offset);

      public abstract Builder setAccountId(String accountId);

      public abstract Builder setCustomerId(String customerId);

      public abstract Builder setQuery(String query);

      public abstract Builder setTags(Map<String, String> tags);

      public abstract Builder setSince(@Nullable Instant since);

      public abstract Builder setSince(Optional<Instant> since);

      public abstract Builder setUntil(@Nullable Instant until);

      public abstract Builder setUntil(Optional<Instant> until);

      public abstract Builder setCardId(String cardId);

      public abstract Builder setSort(String sort);

      public abstract ListRequest build();
    }
  }
}
