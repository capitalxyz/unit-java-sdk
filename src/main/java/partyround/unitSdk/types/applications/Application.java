package partyround.unit.types.applications;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Application {
  public abstract String getApplicationFormId();

  public static Builder builder() {
    return new AutoValue_Application.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setApplicationFormId(String applicationFormId);

    public abstract Application build();
  }
}
