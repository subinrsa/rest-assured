package rest.assured.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.assured.domain.User;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class UserTest {

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://reqres.in";
        basePath = "/api";
    }

    @Test
    public void testListUserMetadata() {
        given().
            params("page", 2).
        when().
            get("/users").
        then().
            statusCode(HttpStatus.SC_OK)
            .body("page", is(2))
            .body("data", is(notNullValue()));
        }

    @Test
    public void testCreateUserSuccess() {
        User user = new User("test", "QA", "test@mailinator.com");
        given().
            contentType(ContentType.JSON)
            .body(user).
        when().
            post("/users").
        then().
            statusCode(HttpStatus.SC_CREATED)
            .body("name", is ("test"))
            .body("job", is("QA"))
            .body("id", is(notNullValue()))
            .body("createdAt", is(notNullValue()));
    }





    }
