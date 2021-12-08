package partyround.unit.types;

import com.google.auto.value.AutoValue;
import java.time.YearMonth;

@AutoValue
public abstract class Statement {
  public abstract String getId();

  public abstract YearMonth getPeriod();

  public abstract Relationship getAccount();

  public abstract Relationship getCustomer();

  public static Builder builder() {
    return new AutoValue_Statement.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setId(String id);

    public abstract Builder setPeriod(YearMonth period);

    public abstract Builder setAccount(Relationship account);

    public abstract Builder setCustomer(Relationship customer);

    public abstract Statement build();
  }
}
