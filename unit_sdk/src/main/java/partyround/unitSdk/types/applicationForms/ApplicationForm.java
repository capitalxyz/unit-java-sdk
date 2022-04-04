package partyround.unit.types.applicationForms;

import com.google.auto.value.AutoValue;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import partyround.unit.types.Relationship;

@AutoValue
public abstract class ApplicationForm {
  public abstract ApplicationFormStage getStage();

  public abstract String getUrl();

  public abstract Optional<ApplicationFormPrefill> getApplicantDetails();

  public abstract Optional<Relationship> getApplication();

  public abstract Optional<Map<String, String>> getTags();

  public static Builder builder() {
    return new AutoValue_ApplicationForm.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setStage(ApplicationFormStage stage);

    public abstract Builder setUrl(String url);

    public abstract Builder setApplicantDetails(@Nullable ApplicationFormPrefill applicantDetails);

    public abstract Builder setApplicantDetails(Optional<ApplicationFormPrefill> applicantDetails);

    public abstract Builder setApplication(Optional<Relationship> application);

    public abstract Builder setTags(Map<String, String> tags);

    public abstract ApplicationForm build();
  }
}
