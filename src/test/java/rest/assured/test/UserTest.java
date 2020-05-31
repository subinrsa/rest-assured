package rest.assured.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import rest.assured.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserTest extends BaseTest{

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
            body(user).
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
