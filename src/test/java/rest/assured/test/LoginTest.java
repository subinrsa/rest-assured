package rest.assured.test;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.assured.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginTest extends BaseTest {

    private static final String LOGIN_ENDPOINT = "/login";

    @BeforeClass
    public static void setupLogin() {
        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectBody(is(notNullValue())).
                build();
    }

    @Test
    public void testUnableToLogin() {
        User user = new User();
        user.setEmail("test@mailinator.com");

        given().
            body(user).
        when().
            post(LOGIN_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST);
//            body("error", is("Missing password"));
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
            statusCode(HttpStatus.SC_OK);
//            body("token", is(notNullValue()));
    }

}
