package partyround.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import partyround.unit.Accounts.CreateDepositAccountRequest;
import partyround.unit.Payments.CreateVerifiedPaymentRequest;
import partyround.unit.types.Direction;
import partyround.unit.types.Relationship;

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
}
