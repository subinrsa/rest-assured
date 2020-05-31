package rest.assured.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import rest.assured.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserTest extends BaseTest{

    private static final String LIST_USERS_ENDPOINT = "/users";

    @Test
    public void testSpecificPageIsDisplayed() {
        given().
            params("page", 2).
        when().
            get(LIST_USERS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK)
            .body("page", is(2))
            .body("data", is(notNullValue()));
        }

    @Test
    public void testAbleToCreateNewUser() {
        User user = new User("test", "QA", "test@mailinator.com");
        given().
            body(user).
        when().
            post(LIST_USERS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_CREATED)
            .body("name", is ("test"))
            .body("job", is("QA"))
            .body("id", is(notNullValue()))
            .body("createdAt", is(notNullValue()));
    }





    }
