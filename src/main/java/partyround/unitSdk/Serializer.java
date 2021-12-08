package partyround.unit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Streams;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import partyround.unit.types.*;
import partyround.unit.types.accounts.AccountStatus;
import partyround.unit.types.accounts.DepositAccount;
import partyround.unit.types.applicationForms.ApplicationForm;
import partyround.unit.types.applicationForms.ApplicationFormPrefill;
import partyround.unit.types.applicationForms.ApplicationFormStage;
import partyround.unit.types.applications.Application;
import partyround.unit.types.payments.ACHPayment;
import partyround.unit.types.payments.BookPayment;
import partyround.unit.types.payments.PaymentStatus;
import partyround.unit.types.payments.WireCounterparty;
import partyround.unit.types.payments.WirePayment;
import partyround.unit.types.transactions.AdjustmentTransaction;
import partyround.unit.types.transactions.Counterparty;
import partyround.unit.types.transactions.DishonoredACHTransaction;
import partyround.unit.types.transactions.FeeTransaction;
import partyround.unit.types.transactions.InterestTransaction;
import partyround.unit.types.transactions.OriginatedACHTransaction;
import partyround.unit.types.transactions.ReceivedACHTransaction;
import partyround.unit.types.transactions.ReturnedACHTransaction;
import partyround.unit.types.transactions.ReturnedReceivedACHTransaction;
import partyround.unit.types.transactions.Transaction;
import partyround.unit.types.transactions.WireTransaction;

// Serialization functions.
public class Serializer {
  private static final ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.registerModule(new Jdk8Module());
  }

  private static String toJsonString(Map<Object, Object> map) {
    try {
      return mapper.writeValueAsString(ImmutableMap.of("data", map));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static ImmutableMap<Object, Object> toMap(Phone phone) {
    return ImmutableMap.builder()
        .put("countryCode", phone.getCountryCode())
        .put("number", phone.getNumber())
        .build();
  }

  private static ImmutableMap<Object, Object> toMap(Relationship relationship) {
    return ImmutableMap.builder()
        .put(
            "data",
            ImmutableMap.builder()
                .put("id", relationship.getId())
                .put("type", relationship.getType())
                .build())
        .build();
  }

  public static String toJson(CustomerTokens.CreateTokenRequest request) {
    return toJsonString(
        ImmutableMap.builder()
            .put("type", "customerToken")
            .put(
                "attributes",
                ImmutableMap.builder()
                    .put("scope", String.join(" ", request.getScopes()))
                    .put("verificationToken", request.getVerificationToken())
                    .put("verificationCode", request.getVerificationCode())
                    .put("expiresIn", request.getExpiresIn().map(Duration::toSeconds))
                    .build())
            .build());
  }

  public static String toJson(CustomerTokens.CreateTokenVerificationRequest request) {
    return toJsonString(
        ImmutableMap.builder()
            .put("type", "customerTokenVerification")
            .put(
                "attributes",
                ImmutableMap.builder()
                    .put("channel", request.getChannel().value)
                    .put("phone", request.getPhone().map(Serializer::toMap))
                    .build())
            .build());
  }

  public static String toJson(ApplicationForms.CreateApplicationFormRequest request) {
    return toJsonString(
        ImmutableMap.builder()
            .put("type", "applicationForm")
            .put(
                "attributes",
                ImmutableMap.builder()
                    .put("applicantDetails", request.getApplicantDetails().map(Serializer::toMap))
                    .put("tags", request.getTags())
                    .build())
            .build());
  }

  public static String toJson(Accounts.CreateDepositAccountRequest request) {
    ImmutableMap.Builder<Object, Object> attributes =
        ImmutableMap.builder()
            .put("depositProduct", request.getDepositProduct())
            .put("idempotencyKey", request.getIdempotencyKey());

    if (request.getTags().isPresent()) {
      attributes.put("tags", request.getTags());
    }

    return toJsonString(
        ImmutableMap.builder()
            .put("type", "depositAccount")
            .put("attributes", attributes.build())
            .put(
                "relationships",
                ImmutableMap.builder().put("customer", toMap(request.getCustomer())).build())
            .build());
  }

  public static String toJson(ACHCounterparties.CreateACHCounterpartyRequest request) {
    return toJsonString(
        ImmutableMap.builder()
            .put("type", "achCounterparty")
            .put(
                "attributes",
                ImmutableMap.builder()
                    .put("name", request.getName())
                    .put("plaidProcessorToken", request.getPlaidProcessorToken())
                    .put("type", request.getType()))
            .put(
                "relationships",
                ImmutableMap.builder()
                    .put(
                        "customer", toMap(Relationship.create("customer", request.getCustomerId())))
                    .build())
            .build());
  }

  public static String toJson(Payments.CreateVerifiedPaymentRequest request) {
    return toJsonString(
        ImmutableMap.builder()
            .put("type", "achPayment")
            .put(
                "attributes",
                ImmutableMap.builder()
                    .put("amount", request.getAmountInCents())
                    .put("direction", request.getDirection().value)
                    .put("idempotencyKey", request.getIdempotencyKey())
                    .put("plaidProcessorToken", request.getPlaidProcessorToken())
                    .put("description", request.getDescription())
                    .put("verifyCounterpartyBalance", request.getVerifyCounterpartyBalance())
                    .build())
            .put(
                "relationships",
                ImmutableMap.builder().put("account", toMap(request.getAccount())).build())
            .build());
  }

  public static String toJson(Payments.CreateBookPaymentRequest request) {
    return toJsonString(
        ImmutableMap.builder()
            .put("type", "bookPayment")
            .put(
                "attributes",
                ImmutableMap.builder()
                    .put("amount", request.getAmountInCents())
                    .put("description", request.getDescription())
                    .put("idempotencyKey", request.getIdempotencyKey())
                    .put("tags", request.getTags())
                    .build())
            .put(
                "relationships",
                ImmutableMap.builder()
                    .put("account", toMap(request.getAccount()))
                    .put("counterpartyAccount", toMap(request.getCounterpartyAccount()))
                    .build())
            .build());
  }

  private static UnitResponse.UnitError toUnitError(JsonNode jsonNode) {
    return UnitResponse.UnitError.ofMessageAndCodeAndDetail(
        jsonNode.get("title").asText(),
        jsonNode.get("status").asText(),
        jsonNode.get("detail").asText());
  }

  public static UnitResponse<JsonNode> toUnitResponse(String in) {
    try {
      JsonNode root = mapper.readTree(in);
      if (root.has("errors")) {
        List<UnitResponse.UnitError> errors =
            Streams.stream(root.get("errors").elements())
                .map(Serializer::toUnitError)
                .collect(ImmutableList.toImmutableList());
        return UnitResponse.ofErrors(errors);
      }
      return UnitResponse.ofResponse(root.get("data"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static CustomerToken toCustomerToken(JsonNode jsonNode) {
    return CustomerToken.create(
        jsonNode.get("attributes").get("token").asText(),
        Duration.ofSeconds(jsonNode.get("attributes").get("expiresIn").asLong()));
  }

  public static VerificationToken toVerificationToken(JsonNode jsonNode) {
    return VerificationToken.create(jsonNode.get("attributes").get("verificationToken").asText());
  }

  public static ApplicationForm toApplicationForm(JsonNode jsonNode) {
    return ApplicationForm.builder()
        .setStage(
            ApplicationFormStage.fromString(jsonNode.get("attributes").get("stage").asText())
                .orElseThrow(() -> new IllegalStateException("invalid application stage")))
        .setUrl(jsonNode.get("attributes").get("url").asText())
        // TODO: deserialize the rest of the fields.
        .build();
  }

  public static Application toApplication(JsonNode jsonNode) {
    return Application.builder()
        .setApplicationFormId(
            jsonNode.get("relationships").get("applicationForm").get("data").get("id").asText())
        .build();
  }

  public static Map<String, String> toTags(JsonNode jsonNode) {
    return mapper.convertValue(jsonNode, new TypeReference<Map<String, String>>() {});
  }

  public static List<ApplicationForm> toApplicationFormList(JsonNode jsonNode) {
    return Streams.stream(jsonNode.elements())
        .map(Serializer::toApplicationForm)
        .collect(ImmutableList.toImmutableList());
  }

  public static ApplicationForms.CreateApplicationFormResponse toCreateApplicationFormResponse(
      JsonNode jsonNode) {
    return ApplicationForms.CreateApplicationFormResponse.builder()
        .setId(jsonNode.get("id").asText())
        .setUrl(jsonNode.get("attributes").get("url").asText())
        // TODO: deserialize the rest of the fields.
        .build();
  }

  public static DepositAccount toDepositAccount(JsonNode jsonNode) {
    return DepositAccount.builder()
        .setId(jsonNode.get("id").asText())
        .setCreatedAt(Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
        .setName(jsonNode.get("attributes").get("name").asText())
        .setDepositProduct(jsonNode.get("attributes").get("depositProduct").asText())
        .setRoutingNumber(jsonNode.get("attributes").get("routingNumber").asText())
        .setAccountNumber(jsonNode.get("attributes").get("accountNumber").asText())
        .setCurrency(jsonNode.get("attributes").get("currency").asText())
        .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
        .setHoldInCents(jsonNode.get("attributes").get("hold").asLong())
        .setAvailableInCents(jsonNode.get("attributes").get("available").asLong())
        .setTags(
            Optional.ofNullable(jsonNode.get("attributes").get("tags")).map(Serializer::toTags))
        .setStatus(AccountStatus.fromString(jsonNode.get("attributes").get("status").asText()))
        .build();
  }

  public static ACHCounterparty toCreateACHCounterpartyResponse(JsonNode jsonNode) {
    return ACHCounterparty.builder()
        .setId(jsonNode.get("id").asText())
        .setCreatedAt(Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
        .setName(jsonNode.get("attributes").get("name").asText())
        .setRoutingNumber(jsonNode.get("attributes").get("routingNumber").asText())
        .setBank(jsonNode.get("attributes").get("bank").asText())
        .setAccountNumber(jsonNode.get("attributes").get("accountNumber").asText())
        .setAccountType(jsonNode.get("attributes").get("accountType").asText())
        .setType(CounterpartyType.fromString(jsonNode.get("attributes").get("type").asText()))
        .setPermissions(
            CounterpartyPermissions.fromString(
                jsonNode.get("attributes").get("permissions").asText()))
        // TODO: parse customer
        .build();
  }

  private static Statement toStatement(JsonNode jsonNode) {
    return Statement.builder()
        .setId(jsonNode.get("id").asText())
        .setPeriod(YearMonth.parse(jsonNode.get("attributes").get("period").asText()))
        .setAccount(
            Relationship.create(
                jsonNode.get("relationships").get("account").get("data").get("type").asText(),
                jsonNode.get("relationships").get("account").get("data").get("id").asText()))
        .setCustomer(
            Relationship.create(
                jsonNode.get("relationships").get("customer").get("data").get("type").asText(),
                jsonNode.get("relationships").get("customer").get("data").get("id").asText()))
        .build();
  }

  public static ImmutableList<Statement> toStatementList(JsonNode jsonNode) {
    return Streams.stream(jsonNode.elements())
        .map(Serializer::toStatement)
        .collect(ImmutableList.toImmutableList());
  }

  public static ACHPayment toACHPayment(JsonNode jsonNode) {
    return ACHPayment.builder()
        .setId(jsonNode.get("id").asText())
        .setCreatedAt(Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
        .setStatus(PaymentStatus.fromString(jsonNode.get("attributes").get("status").asText()))
        .setCounterparty(
            Counterparty.builder()
                .setRoutingNumber(
                    jsonNode.get("attributes").get("counterparty").get("routingNumber").asText())
                .setAccountNumber(
                    jsonNode.get("attributes").get("counterparty").get("accountNumber").asText())
                .setAccountType(
                    jsonNode.get("attributes").get("counterparty").get("accountType").asText())
                .setName(jsonNode.get("attributes").get("counterparty").get("name").asText())
                .build())
        .setDescription(jsonNode.get("attributes").get("description").asText())
        .setAddenda(
            Optional.ofNullable(jsonNode.get("attributes").get("addenda")).map(JsonNode::asText))
        .setReason(
            Optional.ofNullable(jsonNode.get("attributes").get("reason")).map(JsonNode::asText))
        .setSettlementDate(
            Optional.ofNullable(jsonNode.get("attributes").get("settlementDate"))
                .map(JsonNode::asText)
                .map(LocalDate::parse))
        .setDirection(Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
        .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
        .setTags(
            Optional.ofNullable(jsonNode.get("attributes").get("tags")).map(Serializer::toTags))
        .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
        .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
        .build();
  }

  public static Relationship toRelationship(JsonNode jsonNode) {
    return Relationship.create(
        jsonNode.get("data").get("type").asText(), jsonNode.get("data").get("id").asText());
  }

  public static Counterparty toTransactionCounterparty(JsonNode jsonNode) {
    return Counterparty.builder()
        .setRoutingNumber(jsonNode.get("routingNumber").asText())
        .setAccountNumber(jsonNode.get("accountNumber").asText())
        .setAccountType(jsonNode.get("accountType").asText())
        .setName(jsonNode.get("name").asText())
        .build();
  }

  public static Transaction toTransaction(JsonNode jsonNode) {
    String type = jsonNode.get("type").asText();
    switch (type) {
      case "adjustmentTransaction":
        return Transaction.builder()
            .setAdjustmentTransactionOptional(
                AdjustmentTransaction.builder()
                    .setId(jsonNode.get("id").asText())
                    .setCreatedAt(
                        Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
                    .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
                    .setDirection(
                        Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
                    .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
                    .setSummary(jsonNode.get("attributes").get("summary").asText())
                    .setDescription(jsonNode.get("attributes").get("description").asText())
                    .setTags(
                        Optional.ofNullable(jsonNode.get("attributes").get("tags"))
                            .map(Serializer::toTags))
                    .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
                    .build())
            .setType(Transaction.TransactionType.ADJUSTMENT_TRANSACTION)
            .build();
      case "dishonoredReceivedAchTransaction":
        return Transaction.builder()
            .setDishonoredACHTransactionOptional(
                DishonoredACHTransaction.builder()
                    .setId(jsonNode.get("id").asText())
                    .setCreatedAt(
                        Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
                    .setDirection(
                        Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
                    .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
                    .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
                    .setSummary(jsonNode.get("attributes").get("summary").asText())
                    .setDescription(jsonNode.get("attributes").get("description").asText())
                    .setCompanyName(jsonNode.get("attributes").get("companyName").asText())
                    .setCounterpartyRoutingNumber(
                        jsonNode.get("attributes").get("counterpartyRoutingNumber").asText())
                    .setTraceNumber(jsonNode.get("attributes").get("traceNumber").asText())
                    .setSECCode(
                        Optional.ofNullable(jsonNode.get("attributes").get("secCode"))
                            .map(JsonNode::asText))
                    .setTags(
                        Optional.ofNullable(jsonNode.get("attributes").get("tags"))
                            .map(Serializer::toTags))
                    .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
                    .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
                    .build())
            .setType(Transaction.TransactionType.DISHONORED_ACH_TRANSACTION)
            .build();
      case "feeTransaction":
        return Transaction.builder()
            .setFeeTransactionOptional(
                FeeTransaction.builder()
                    .setId(jsonNode.get("id").asText())
                    .setCreatedAt(
                        Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
                    .setDirection(
                        Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
                    .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
                    .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
                    .setSummary(jsonNode.get("attributes").get("summary").asText())
                    .setTags(
                        Optional.ofNullable(jsonNode.get("attributes").get("tags"))
                            .map(Serializer::toTags))
                    .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
                    .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
                    .setRelatedTransaction(
                        Optional.ofNullable(jsonNode.get("relationships").get("relatedTransaction"))
                            .map(Serializer::toRelationship))
                    .build())
            .setType(Transaction.TransactionType.FEE_TRANSACTION)
            .build();
      case "interestTransaction":
        return Transaction.builder()
            .setInterestTransactionOptional(
                InterestTransaction.builder()
                    .setId(jsonNode.get("id").asText())
                    .setCreatedAt(
                        Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
                    .setDirection(
                        Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
                    .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
                    .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
                    .setSummary(jsonNode.get("attributes").get("summary").asText())
                    .setTags(
                        Optional.ofNullable(jsonNode.get("attributes").get("tags"))
                            .map(Serializer::toTags))
                    .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
                    .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
                    .build())
            .setType(Transaction.TransactionType.INTEREST_TRANSACTION)
            .build();
      case "originatedAchTransaction":
        return Transaction.builder()
            .setOriginatedACHTransactionOptional(
                OriginatedACHTransaction.builder()
                    .setId(jsonNode.get("id").asText())
                    .setCreatedAt(
                        Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
                    .setDirection(
                        Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
                    .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
                    .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
                    .setSummary(jsonNode.get("attributes").get("summary").asText())
                    .setDescription(jsonNode.get("attributes").get("description").asText())
                    .setCounterparty(
                        toTransactionCounterparty(jsonNode.get("attributes").get("counterparty")))
                    .setTags(
                        Optional.ofNullable(jsonNode.get("attributes").get("tags"))
                            .map(Serializer::toTags))
                    .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
                    .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
                    .setPayment(toRelationship(jsonNode.get("relationships").get("payment")))
                    .build())
            .setType(Transaction.TransactionType.ORIGINATED_ACH_TRANSACTION)
            .build();
      case "receivedAchTransaction":
        return Transaction.builder()
            .setReceivedACHTransactionOptional(
                ReceivedACHTransaction.builder()
                    .setId(jsonNode.get("id").asText())
                    .setCreatedAt(
                        Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
                    .setDirection(
                        Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
                    .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
                    .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
                    .setSummary(jsonNode.get("attributes").get("summary").asText())
                    .setDescription(jsonNode.get("attributes").get("description").asText())
                    .setAddenda(
                        Optional.ofNullable(jsonNode.get("attributes").get("addenda"))
                            .map(JsonNode::asText))
                    .setCompanyName(jsonNode.get("attributes").get("companyName").asText())
                    .setCounterpartyRoutingNumber(
                        jsonNode.get("attributes").get("counterpartyRoutingNumber").asText())
                    .setTraceNumber(
                        Optional.ofNullable(jsonNode.get("attributes").get("traceNumber"))
                            .map(JsonNode::asText))
                    .setSECCode(
                        Optional.ofNullable(jsonNode.get("attributes").get("secCode"))
                            .map(JsonNode::asText))
                    .setTags(
                        Optional.ofNullable(jsonNode.get("attributes").get("tags"))
                            .map(Serializer::toTags))
                    .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
                    .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
                    .build())
            .setType(Transaction.TransactionType.RECEIVED_ACH_TRANSACTION)
            .build();
      case "returnedAchTransaction":
        return Transaction.builder()
            .setReturnedACHTransactionOptional(
                ReturnedACHTransaction.builder()
                    .setId(jsonNode.get("id").asText())
                    .setCreatedAt(
                        Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
                    .setDirection(
                        Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
                    .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
                    .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
                    .setSummary(jsonNode.get("attributes").get("summary").asText())
                    .setCompanyName(jsonNode.get("attributes").get("companyName").asText())
                    .setCounterpartyName(
                        jsonNode.get("attributes").get("counterpartyName").asText())
                    .setCounterpartyRoutingNumber(
                        jsonNode.get("attributes").get("counterpartyRoutingNumber").asText())
                    .setReason(jsonNode.get("attributes").get("reason").asText())
                    .setTags(
                        Optional.ofNullable(jsonNode.get("attributes").get("tags"))
                            .map(Serializer::toTags))
                    .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
                    .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
                    .setPayment(toRelationship(jsonNode.get("relationships").get("payment")))
                    .build())
            .setType(Transaction.TransactionType.RECEIVED_ACH_TRANSACTION)
            .build();
      case "returnedReceivedAchTransaction":
        return Transaction.builder()
            .setReturnedReceivedACHTransactionOptional(
                ReturnedReceivedACHTransaction.builder()
                    .setId(jsonNode.get("id").asText())
                    .setCreatedAt(
                        Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
                    .setDirection(
                        Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
                    .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
                    .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
                    .setSummary(jsonNode.get("attributes").get("summary").asText())
                    .setCompanyName(jsonNode.get("attributes").get("companyName").asText())
                    .setReason(jsonNode.get("attributes").get("reason").asText())
                    .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
                    .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
                    .setReturned(toRelationship(jsonNode.get("relationships").get("returned")))
                    .build())
            .setType(Transaction.TransactionType.RECEIVED_ACH_TRANSACTION)
            .build();
      case "wireTransaction":
        return Transaction.builder()
            .setWireTransactionOptional(
                WireTransaction.builder()
                    .setId(jsonNode.get("id").asText())
                    .setCreatedAt(
                        Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
                    .setDirection(
                        Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
                    .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
                    .setBalanceInCents(jsonNode.get("attributes").get("balance").asLong())
                    .setSummary(jsonNode.get("attributes").get("summary").asText())
                    .setCounterparty(
                        toTransactionCounterparty(jsonNode.get("attributes").get("counterparty")))
                    .setDescription(jsonNode.get("attributes").get("description").asText())
                    .setTags(
                        Optional.ofNullable(jsonNode.get("attributes").get("tags"))
                            .map(Serializer::toTags))
                    .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
                    .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
                    .build())
            .setType(Transaction.TransactionType.WIRE_TRANSACTION)
            .build();
      default:
        throw new IllegalStateException("unsupported transaction type: " + type);
    }
  }

  public static Address toAddress(JsonNode jsonNode) {
    return Address.builder()
        .setStreet(jsonNode.get("street").asText())
        .setStreet2(jsonNode.get("street2").asText())
        .setCity(jsonNode.get("city").asText())
        .setState(jsonNode.get("state").asText())
        .setPostalCode(jsonNode.get("postalCode").asText())
        .setCountry(jsonNode.get("country").asText())
        .build();
  }

  public static WireCounterparty toWireCounterparty(JsonNode jsonNode) {
    return WireCounterparty.builder()
        .setName(jsonNode.get("name").asText())
        .setRoutingNumber(jsonNode.get("routingNumber").asText())
        .setAccountNumber(jsonNode.get("accountNumber").asText())
        .setAddress(toAddress(jsonNode.get("address")))
        .build();
  }

  public static WirePayment toWirePayment(JsonNode jsonNode) {
    return WirePayment.builder()
        .setId(jsonNode.get("id").asText())
        .setCreatedAt(Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
        .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
        .setDirection(Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
        .setDescription(jsonNode.get("attributes").get("description").asText())
        .setCounterparty(toWireCounterparty(jsonNode.get("attributes").get("counterparty")))
        .setStatus(PaymentStatus.fromString(jsonNode.get("attributes").get("status").asText()))
        .setReason(
            Optional.ofNullable(jsonNode.get("attributes").get("reason")).map(JsonNode::asText))
        .setTags(
            Optional.ofNullable(jsonNode.get("attributes").get("tags")).map(Serializer::toTags))
        .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
        .setCustomer(toRelationship(jsonNode.get("relationships").get("customer")))
        .setTransaction(toRelationship(jsonNode.get("relationships").get("transaction")))
        .build();
  }

  public static ImmutableMap<Object, Object> toMap(Address address) {
    return ImmutableMap.builder()
        .put("street", address.getStreet())
        .put("street2", address.getStreet2())
        .put("city", address.getCity())
        .put("state", address.getState())
        .put("postalCode", address.getPostalCode())
        .put("country", address.getCountry())
        .build();
  }

  public static ImmutableMap<Object, Object> toMap(WireCounterparty counterparty) {
    return ImmutableMap.builder()
        .put("name", counterparty.getName())
        .put("routingNumber", counterparty.getRoutingNumber())
        .put("accountNumber", counterparty.getAccountNumber())
        .put("address", toMap(counterparty.getAddress()))
        .build();
  }

  public static String toJson(Payments.CreateWirePaymentRequest request) {
    return toJsonString(
        ImmutableMap.builder()
            .put("type", "wirePayment")
            .put(
                "attributes",
                ImmutableMap.builder()
                    .put("amount", request.getAmountInCents())
                    .put("direction", Direction.CREDIT.value)
                    .put("idempotencyKey", request.getIdempotencyKey())
                    .put("counterparty", toMap(request.getCounterparty()))
                    .put("description", request.getDescription())
                    .build())
            .put(
                "relationships",
                ImmutableMap.builder().put("account", toMap(request.getAccount())).build())
            .build());
  }

  public static ImmutableList<Transaction> toTransactionsList(JsonNode jsonNode) {
    return Streams.stream(jsonNode.elements())
        .map(Serializer::toTransaction)
        .collect(ImmutableList.toImmutableList());
  }

  public static ImmutableMap<Object, Object> toMap(FullName fullName) {
    return ImmutableMap.builder()
        .put("first", fullName.getFirst())
        .put("last", fullName.getLast())
        .build();
  }

  public static ImmutableMap<Object, Object> toMap(ApplicationFormPrefill applicationFormPrefill) {
    return ImmutableMap.builder()
        .put("applicationType", applicationFormPrefill.getApplicationType())
        .put("fullName", applicationFormPrefill.getFullName().map(Serializer::toMap))
        .put("ssn", applicationFormPrefill.getSsn())
        .put("passport", applicationFormPrefill.getPassport())
        .put("nationality", applicationFormPrefill.getNationality())
        .put("dateOfBirth", applicationFormPrefill.getDateOfBirth().map(LocalDate::toString))
        .put("email", applicationFormPrefill.getEmail())
        .put("name", applicationFormPrefill.getName())
        .put("stateOfIncorporation", applicationFormPrefill.getStateOfIncorporation())
        .put("entityType", applicationFormPrefill.getEntityType())
        .put("website", applicationFormPrefill.getWebsite())
        .put("dba", applicationFormPrefill.getDba())
        .put("ein", applicationFormPrefill.getEin())
        .put("address", applicationFormPrefill.getAddress().map(Serializer::toMap))
        .put("phone", applicationFormPrefill.getPhone().map(Serializer::toMap))
        .build();
  }

  public static BookPayment toBookPayment(JsonNode jsonNode) {
    return BookPayment.builder()
        .setId(jsonNode.get("id").asText())
        .setCreatedAt(Instant.parse(jsonNode.get("attributes").get("createdAt").asText()))
        .setAmountInCents(jsonNode.get("attributes").get("amount").asLong())
        .setDirection(Direction.fromString(jsonNode.get("attributes").get("direction").asText()))
        .setDescription(jsonNode.get("attributes").get("description").asText())
        .setStatus(PaymentStatus.fromString(jsonNode.get("attributes").get("status").asText()))
        .setReason(
            Optional.ofNullable(jsonNode.get("attributes").get("reason")).map(JsonNode::asText))
        .setTags(
            Optional.ofNullable(jsonNode.get("attributes").get("tags")).map(Serializer::toTags))
        .setAccount(toRelationship(jsonNode.get("relationships").get("account")))
        .setCustomer(
            Optional.ofNullable(jsonNode.get("relationships").get("customer"))
                .map(Serializer::toRelationship))
        .setCustomers(
            Optional.ofNullable(jsonNode.get("relationships").get("customer"))
                .map(
                    (node) ->
                        Streams.stream(node.elements())
                            .map(Serializer::toRelationship)
                            .collect(ImmutableList.toImmutableList())))
        .setCounterpartyAccount(
            toRelationship(jsonNode.get("relationships").get("counterpartyAccount")))
        .setCounterpartyCustomer(
            toRelationship(jsonNode.get("relationships").get("counterpartyCustomer")))
        .setTransaction(toRelationship(jsonNode.get("relationships").get("transaction")))
        .build();
  }
}
