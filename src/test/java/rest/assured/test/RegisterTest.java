package rest.assured.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import rest.assured.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class RegisterTest extends BaseTest {

    private static final String REGISTER_ENDPOINT = "/register";

    @Test
    public void testRegisterUnsuccessful() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        given().
            body(user).
        when().
            post(REGISTER_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("error", is("Missing password"));
    }

    @Test
    public void testRegisterSuccessful() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");
        given().
            body(user).
        when().
            post(REGISTER_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK)
            .body("id", is(notNullValue()))
            .body("token", is(notNullValue()));
    }


}
