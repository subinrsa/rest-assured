package rest.assured.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import rest.assured.domain.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTest extends BaseTest{

    private static final String LIST_USERS_ENDPOINT = "/users";
    private static final String SHOW_USER_ENDPOINT = "/users/{userId}";

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
    public void testSizeOfItemsDisplayedAreTheSameAsPerPage() {
        int expectedPage = 2;
        int expectedItemsPerPage = getExpectedItemsPerPage(expectedPage);

        given().
            params("page", expectedPage).
        when().
            get(LIST_USERS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK)
            .body(
                "page", is(expectedPage),
"data.size()", is(expectedItemsPerPage),
                    "data.findAll { it.avatar.startsWith('https://s3.amazonaws.com') }.size()", is(expectedItemsPerPage)
            );
    }

    @Test
    public void testAbleToCreateNewUser() {
        User user = new User("test", "QA", "test@mailinator.com");
        given().
            body(user). // SERIALIZATION
        when().
            post(LIST_USERS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_CREATED)
            .body("name", is ("test"))
            .body("job", is("QA"))
            .body("id", is(notNullValue()))
            .body("createdAt", is(notNullValue()));
    }

    @Test
    public void showSpecificUser() {
        User user = given().
                        pathParam("userId", 2).
                    when().
                        get(SHOW_USER_ENDPOINT).
            //      thenReturn().as(User.class); // DE-SERIALIZATION
                    then().
                    statusCode(HttpStatus.SC_OK).
            //         body("data", is(notNullValue()));
                    extract().
                        body().jsonPath().getObject("data", User.class); // DE-SERIALIZATION

        assertThat(user.getEmail(), containsString("@reqres.in"));
        assertThat(user.getName(), containsString("Janet"));
        assertThat(user.getLastName(), containsString("Weaver"));
    }

    private int getExpectedItemsPerPage(int page) {
        return given().
                param("page", page).
            when().
                get(LIST_USERS_ENDPOINT).
            then().
                statusCode(HttpStatus.SC_OK).
            extract().
                path("per_page");
        }

    }
