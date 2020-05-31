package rest.assured.test;

import org.apache.http.HttpStatus;
import org.junit.Test;
import rest.assured.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginTest extends BaseTest {

    private static final String LOGIN_ENDPOINT = "/login";

    @Test
    public void testUnableToLogin() {
        User user = new User();
        user.setEmail("test@mailinator.com");

        given().
            body(user).
        when().
            post(LOGIN_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST).
            body("error", is("Missing password"));
    }

    @Test
    public void testAbleToLogin() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("cityslicka");

        given().
            body(user).
        when().
            post(LOGIN_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body("token", is(notNullValue()));
    }

}
