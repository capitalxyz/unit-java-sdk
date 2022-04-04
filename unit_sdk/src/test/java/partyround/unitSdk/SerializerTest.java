package partyround.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import partyround.unit.Accounts.CloseDepositAccountRequest;
import partyround.unit.Accounts.CreateDepositAccountRequest;
import partyround.unit.Payments.CreateVerifiedPaymentRequest;
import partyround.unit.types.Address;
import partyround.unit.types.Direction;
import partyround.unit.types.Relationship;
import partyround.unit.types.accounts.CloseAccountFraudReason;
import partyround.unit.types.accounts.CloseAccountReason;
import partyround.unit.types.applicationForms.ApplicationForm;
import partyround.unit.types.applicationForms.ApplicationFormPrefill;
import partyround.unit.types.applicationForms.ApplicationType;
import partyround.unit.types.payments.WireCounterparty;

public class SerializerTest {
  private static final ObjectMapper mapper = new ObjectMapper();

  private static final String resourceDir = "resources";

  private String getJsonString(String filename) throws IOException, NullPointerException {
    return new String(
        Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(filename)
            .readAllBytes());
  }

  private void assertJsonEquals(String expected, String actual) throws IOException {
    assertEquals(mapper.readTree(expected), mapper.readTree(actual));
  }

  @Test
  void testToACHPaymentRejectedDailyLimitExceeded() throws IOException {
    String response = getJsonString("ACHPaymentRejectedDailyLimitExceeded.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    Serializer.toACHPayment(tree);
  }

  @Test
  void testToACHPaymentPending() throws IOException {
    String response = getJsonString("ACHPaymentPending.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    Serializer.toACHPayment(tree);
  }

  @Test
  void testToBookPayment() throws IOException {
    String response = getJsonString("BookPayment.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    Serializer.toBookPayment(tree);
  }

  @Test
  void testToBookPaymentRejected() throws IOException {
    String response = getJsonString("BookPaymentRejected.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    Serializer.toBookPayment(tree);
  }

  @Test
  void testToDepositAccount() throws IOException {
    String response = getJsonString("DepositAccount.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    Serializer.toDepositAccount(tree);
  }

  @Test
  void testToStatementList() throws IOException {
    String response = getJsonString("StatementList.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    Serializer.toStatementList(tree);
  }

  @Test
  void testToTransactionsList() throws IOException {
    String response = getJsonString("TransactionsList.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    Serializer.toTransactionsList(tree);
  }

  @Test
  void testCloseDepositAccountRequestWithoutReasonToJson() throws IOException {
    CloseDepositAccountRequest request =
        CloseDepositAccountRequest.builder().setId("123456").build();

    assertEquals(request.getId(), "123456");
    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CloseDepositAccountRequestWithoutReason.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testCloseDepositAccountRequestWithReasonByCustomerToJson() throws IOException {
    CloseDepositAccountRequest request =
        CloseDepositAccountRequest.builder()
            .setId("123456")
            .setReason(CloseAccountReason.BY_CUSTOMER)
            .build();

    assertEquals(request.getId(), "123456");
    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CloseDepositAccountRequestWithReasonByCustomer.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testCloseDepositAccountRequestWithReasonFraudToJson() throws IOException {
    CloseDepositAccountRequest request =
        CloseDepositAccountRequest.builder()
            .setId("123456")
            .setReason(CloseAccountReason.FRAUD)
            .setFraudReason(CloseAccountFraudReason.ACCOUNT_ACTIVITY)
            .build();

    assertEquals(request.getId(), "123456");
    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CloseDepositAccountRequestWithReasonFraud.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testCreateDepositAccountRequestToJson() throws IOException {
    CreateDepositAccountRequest request =
        CreateDepositAccountRequest.builder()
            .setDepositProduct("test")
            .setCustomer(Relationship.create("moo", "cow"))
            .build();

    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CreateDepositAccountRequest.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testCreateVerifiedPaymentRequestToJson() throws IOException {
    CreateVerifiedPaymentRequest request =
        CreateVerifiedPaymentRequest.builder()
            .setIdempotencyKey(UUID.fromString("db36c9c2-f3bb-4499-9aaf-c1d6ec7b161d").toString())
            .setAmountInCents(new BigDecimal(1000).longValue())
            .setDirection(Direction.fromString("Debit"))
            .setPlaidProcessorToken("unitProcessorToken")
            .setDescription("PartyRound")
            .setCounterpartyName("Name")
            .setAccount(Relationship.create("depositAccount", "externalDepositId"))
            .setVerifyCounterpartyBalance(true)
            .build();

    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CreateVerifiedPaymentRequest.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testCreateVerifiedPaymentRequestToJson_withNull() throws IOException {
    CreateVerifiedPaymentRequest request =
        CreateVerifiedPaymentRequest.builder()
            .setIdempotencyKey(UUID.fromString("db36c9c2-f3bb-4499-9aaf-c1d6ec7b161d").toString())
            .setAmountInCents(new BigDecimal(1000).longValue())
            .setDirection(Direction.fromString("Debit"))
            .setPlaidProcessorToken("unitProcessorToken")
            .setDescription("PartyRound")
            .setCounterpartyName((String) null)
            .setAccount(Relationship.create("depositAccount", "externalDepositId"))
            .setVerifyCounterpartyBalance(true)
            .build();

    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CreateVerifiedPaymentRequest_emptyCounterpartyName.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testCreateVerifiedPaymentRequestToJson_withOptionalEmpty() throws IOException {
    CreateVerifiedPaymentRequest request =
        CreateVerifiedPaymentRequest.builder()
            .setIdempotencyKey(UUID.fromString("db36c9c2-f3bb-4499-9aaf-c1d6ec7b161d").toString())
            .setAmountInCents(new BigDecimal(1000).longValue())
            .setDirection(Direction.fromString("Debit"))
            .setPlaidProcessorToken("unitProcessorToken")
            .setDescription("PartyRound")
            .setCounterpartyName(Optional.empty())
            .setAccount(Relationship.create("depositAccount", "externalDepositId"))
            .setVerifyCounterpartyBalance(true)
            .build();

    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CreateVerifiedPaymentRequest_emptyCounterpartyName.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testWirePaymentRequestToJson_noStreet2() throws IOException {
    Payments.CreateWirePaymentRequest request =
        Payments.CreateWirePaymentRequest.builder()
            .setIdempotencyKey(UUID.fromString("db36c9c2-f3bb-4499-9aaf-c1d6ec7b161d").toString())
            .setAmountInCents(new BigDecimal(200).longValue())
            .setDescription("PartyRound")
            .setCounterparty(
                WireCounterparty.builder()
                    .setName("Test Person")
                    .setAddress(
                        Address.builder()
                            .setStreet("20 Ingram St")
                            .setCity("Forest Hills")
                            .setState("CA")
                            .setPostalCode("11375")
                            .setCountry("US")
                            .build())
                    .setAccountNumber("1000000001")
                    .setRoutingNumber("021000021")
                    .build())
            .setAccount(Relationship.create("depositAccount", "143427"))
            .build();

    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CreateWirePaymentRequest_noStreet2.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testWirePaymentRequestToJson_street2() throws IOException {
    Payments.CreateWirePaymentRequest request =
        Payments.CreateWirePaymentRequest.builder()
            .setIdempotencyKey(UUID.fromString("db36c9c2-f3bb-4499-9aaf-c1d6ec7b161d").toString())
            .setAmountInCents(new BigDecimal(200).longValue())
            .setDescription("PartyRound")
            .setCounterparty(
                WireCounterparty.builder()
                    .setName("Test Person")
                    .setAddress(
                        Address.builder()
                            .setStreet("20 Ingram St")
                            .setStreet2("#5")
                            .setCity("Forest Hills")
                            .setState("CA")
                            .setPostalCode("11375")
                            .setCountry("US")
                            .build())
                    .setAccountNumber("1000000001")
                    .setRoutingNumber("021000021")
                    .build())
            .setAccount(Relationship.create("depositAccount", "143427"))
            .build();

    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CreateWirePaymentRequest_street2.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testToWirePayment_noStreet2() throws IOException {
    String response = getJsonString("WirePayment_noStreet2.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    Serializer.toWirePayment(tree);
  }

  @Test
  void testToWirePayment_street2() throws IOException {
    String response = getJsonString("WirePayment_street2.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    Serializer.toWirePayment(tree);
  }

  @Test
  void testCreateApplicationFormRequestToJson_business() throws IOException {
    Address address =
        Address.builder()
            .setStreet("123 Biz Street")
            .setCity("New York")
            .setState("NY")
            .setPostalCode("10003")
            .setCountry("US")
            .build();
    HashSet<ApplicationType> applicationTypes = new HashSet<>();
    applicationTypes.add(ApplicationType.BUSINESS);

    ApplicationForms.CreateApplicationFormRequest request =
        ApplicationForms.CreateApplicationFormRequest.builder()
            .setAllowedApplicationTypes(applicationTypes)
            .setApplicantDetails(
                ApplicationFormPrefill.builder()
                    .setApplicationType(ApplicationType.BUSINESS)
                    .setName("BizCo Inc")
                    .setEin("123456789")
                    .setStateOfIncorporation("NY")
                    .setAddress(address)
                    .build())
            .build();
    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CreateApplicationFormRequest_business.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testCreateApplicationFormRequestToJson_business2() throws IOException {
    Address address =
        Address.builder()
            .setStreet("123 Biz Street")
            .setStreet2("#1")
            .setCity("New York")
            .setState("NY")
            .setPostalCode("10003")
            .setCountry("US")
            .build();
    HashSet<ApplicationType> applicationTypes = new HashSet<>();
    applicationTypes.add(ApplicationType.BUSINESS);

    ApplicationForms.CreateApplicationFormRequest request =
        ApplicationForms.CreateApplicationFormRequest.builder()
            .setAllowedApplicationTypes(applicationTypes)
            .setApplicantDetails(
                ApplicationFormPrefill.builder()
                    .setApplicationType(ApplicationType.BUSINESS)
                    .setName("BizCo Inc")
                    .setEin("123456789")
                    .setAddress(address)
                    .build())
            .build();
    String requestString = Serializer.toJson(request);
    String expected = getJsonString("CreateApplicationFormRequest_business2.json");
    assertJsonEquals(requestString, expected);
  }

  @Test
  void testToApplicationForm_noApplication() throws IOException {
    String response = getJsonString("ApplicationForm_noApplication.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    ApplicationForm form = Serializer.toApplicationForm(tree);

    assertFalse(form.getApplication().isPresent());
  }

  @Test
  void testToApplicationForm_application() throws IOException {
    String response = getJsonString("ApplicationForm_application.json");
    JsonNode tree = mapper.readTree(response.getBytes(StandardCharsets.UTF_8));
    ApplicationForm form = Serializer.toApplicationForm(tree);

    assertTrue(form.getApplication().isPresent());
    assertEquals("12345", form.getApplication().get().getId());
  }
}
