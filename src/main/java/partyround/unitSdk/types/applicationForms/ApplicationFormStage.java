package partyround.unit.types.applicationForms;

import java.util.Optional;

public enum ApplicationFormStage {
  CHOOSE_BUSINESS_OR_INDIVIDUAL("ChooseBusinessOrIndividual"),
  ENTER_INDIVIDUAL_INFORMATION("EnterIndividualInformation"),
  INDIVIDUAL_PHONE_VERIFICATION("IndividualPhoneVerification"),
  INDIVIDUAL_APPLICATION_CREATED("IndividualApplicationCreated"),
  ENTER_BUSINESS_INFORMATION("EnterBusinessInformation"),
  ENTER_OFFICER_INFORMATION("EnterOfficerInformation"),
  BUSINESS_PHONE_VERIFICATION("BusinessPhoneVerification"),
  ENTER_BENEFICIAL_OWNERS_INFORMATION("EnterBeneficialOwnersInformation"),
  BUSINESS_APPLICATION_CREATED("BusinessApplicationCreated"),
  ENTER_SOLE_PROPRIETORSHIP_INFORMATION("EnterSoleProprietorshipInformation"),
  SOLE_PROPRIETORSHIP_PHONE_VERIFICATION("SoleProprietorshipPhoneVerification"),
  SOLE_PROPRIETORSHIP_APPLICATION_CREATED("SoleProprietorshipApplicationCreated");

  private final String stageName;

  ApplicationFormStage(String stageName) {
    this.stageName = stageName;
  }

  public static Optional<ApplicationFormStage> fromString(String stageName) {
    for (ApplicationFormStage stage : ApplicationFormStage.values()) {
      if (stage.stageName.equals(stageName)) {
        return Optional.of(stage);
      }
    }
    return Optional.empty();
  }
}
