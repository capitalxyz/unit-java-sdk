package partyround.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.http.HttpClient;
import org.junit.jupiter.api.Test;

class UnitContextTest {

  @Test
  void testAuthorizationHeader() {
    UnitContext context =
        UnitContext.builder()
            .setHttpClient(HttpClient.newHttpClient())
            .setToken("cow-token")
            .setApiUrl("http://asdfasdf")
            .build();
    assertEquals(
        "Bearer cow-token",
        context
            .newRequestBuilderForPath("mooo")
            .build()
            .headers()
            .firstValue("Authorization")
            .get());
  }

  @Test
  void testUri() {
    UnitContext context =
        UnitContext.builder()
            .setHttpClient(HttpClient.newHttpClient())
            .setToken("cow-token")
            .setApiUrl("http://moo.api.cow")
            .build();
    assertEquals(
        "http://moo.api.cow/path-of-cows",
        context.newRequestBuilderForPath("path-of-cows").build().uri().toString());
  }
}
