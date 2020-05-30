package rest.assured.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.assured.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class RegisterTest {

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void testRegisterUnsuccessful() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        given().
            contentType(ContentType.JSON).
            body(user).
        when().
            post("/register").
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
            contentType(ContentType.JSON).
            body(user).
        when().
            post("/register").
        then().
            statusCode(HttpStatus.SC_OK)
            .body("id", is(notNullValue()))
            .body("token", is(notNullValue()));
    }


}
