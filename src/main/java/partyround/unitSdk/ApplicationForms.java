package partyround.unit;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import partyround.unit.types.UnitResponse;
import partyround.unit.types.applicationForms.ApplicationForm;
import partyround.unit.types.applicationForms.ApplicationFormPrefill;
import partyround.unit.types.applicationForms.ApplicationType;

public class ApplicationForms {
  @AutoValue
  public abstract static class CreateApplicationFormResponse {
    public abstract String getId();

    public abstract Optional<Map<String, String>> getTags();

    public abstract String getUrl();

    public abstract Optional<ApplicationFormPrefill> getApplicantDetails();

    public static Builder builder() {
      return new AutoValue_ApplicationForms_CreateApplicationFormResponse.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
      abstract Builder setId(String id);

      abstract Builder setTags(Map<String, String> tags);

      abstract Builder setUrl(String url);

      abstract Builder setApplicantDetails(ApplicationFormPrefill applicantDetails);

      abstract CreateApplicationFormResponse build();
    }
  }

  @AutoValue
  public abstract static class CreateApplicationFormRequest {
    public abstract Optional<Map<String, String>> getTags();

    public abstract Optional<ApplicationFormPrefill> getApplicantDetails();

    public abstract Set<ApplicationType> getAllowedApplicationTypes();

    public UnitResponse<CreateApplicationFormResponse> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath("application-forms")
              .POST(BodyPublishers.ofString(Serializer.toJson(this)))
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body())
          .map(Serializer::toCreateApplicationFormResponse);
    }

    public static Builder builder() {
      return new AutoValue_ApplicationForms_CreateApplicationFormRequest.Builder()
          .setAllowedApplicationTypes(ImmutableSet.of());
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setTags(Map<String, String> tags);

      public abstract Builder setApplicantDetails(ApplicationFormPrefill applicantDetails);

      public abstract Builder setAllowedApplicationTypes(
          Set<ApplicationType> allowedApplicationTypes);

      public abstract CreateApplicationFormRequest build();
    }
  }

  @AutoValue
  public abstract static class GetApplicationFormRequest {
    public abstract String getApplicationFormId();

    public static Builder builder() {
      return new AutoValue_ApplicationForms_GetApplicationFormRequest.Builder();
    }

    public UnitResponse<ApplicationForm> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request =
          context
              .newRequestBuilderForPath(
                  String.format("application-forms/%s", getApplicationFormId()))
              .GET()
              .build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toApplicationForm);
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setApplicationFormId(String applicationFormId);

      public abstract GetApplicationFormRequest build();
    }
  }

  public static class ListApplicationFormRequest {
    public static UnitResponse<List<ApplicationForm>> executeWith(UnitContext context)
        throws IOException, InterruptedException {
      HttpRequest request = context.newRequestBuilderForPath("application-forms").GET().build();
      HttpResponse<String> response =
          context.getHttpClient().send(request, BodyHandlers.ofString());
      return Serializer.toUnitResponse(response.body()).map(Serializer::toApplicationFormList);
    }
  }
}
