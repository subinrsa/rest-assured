package rest.assured.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.assured.domain.User;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AlternativeUserTest {

    private static final String BASE_URL = "https://reqres.in";
    private static final String BASE_PATH = "/api";
    private static final String LIST_USERS_ENDPOINT = "/users";
    private static final String SHOW_USER_ENDPOINT = "/users/{userId}";

    @BeforeClass
    public static void altSetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testSpecificPageIsDisplayed() {
        String uri = getUri(LIST_USERS_ENDPOINT);

        given().
                params("page", 2).
                when().
                get(uri).
                then().
                statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("page", is(2))
                .body("data", is(notNullValue()));
    }

    @Test
    public void testSizeOfItemsDisplayedAreTheSameAsPerPage() {
        int expectedPage = 2;
        int expectedItemsPerPage = getExpectedItemsPerPage(expectedPage);
        String uri = getUri(LIST_USERS_ENDPOINT);
        given().
                param("page", expectedPage).
                when().
                get(uri).
                then().
                statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body(
                        "page", is(expectedPage),
                        "data.size()", is(expectedItemsPerPage)
                        // "data.findAll { it.avatar.startsWith('https://s3.amazonaws.com') }.size()", is(expectedItemsPerPage)
                );
    }

    @Test
    public void testAbleToCreateNewUser() {
        String uri = getUri(LIST_USERS_ENDPOINT);
        // User user = new User("test", "QA", "test@mailinator.com","password", "dude");
        // Instead using HashMap (dictionary)
        Map<String, String> user = new HashMap<String, String>();
        user.put("name", "test");
        user.put("job", "QA");
        given().
                contentType(ContentType.JSON).
                body(user). // SERIALIZATION
                when().
                post(uri).
                then().
                statusCode(HttpStatus.SC_CREATED)
                .contentType(ContentType.JSON)
                .body("name", is("test"))
                .body("job", is("QA"))
                .body("id", is(notNullValue()))
                .body("createdAt", is(notNullValue()));
    }

    @Test
    public void showSpecificUser() {
        String uri = getUri(SHOW_USER_ENDPOINT);
        User user = given().
                pathParam("userId", 2).
                when().
                get(uri).
                //      thenReturn().as(User.class); // DE-SERIALIZATION
                        then().
                        statusCode(HttpStatus.SC_OK).
                        contentType(ContentType.JSON).
                //         body("data", is(notNullValue()));
                        extract().
                        body().jsonPath().getObject("data", User.class); // DE-SERIALIZATION

        assertThat(user.getEmail(), containsString("@reqres.in"));
        assertThat(user.getName(), containsString("Janet"));
        assertThat(user.getLastName(), containsString("Weaver"));
    }

    private int getExpectedItemsPerPage(int page) {
        String uri = getUri(LIST_USERS_ENDPOINT);
        return given().
                param("page", page).
                when().
                get(uri).
                then().
                statusCode(HttpStatus.SC_OK).
                contentType(ContentType.JSON).
                extract().
                path("per_page");
    }

    private String getUri(String endpoint) {
        return BASE_URL + BASE_PATH + endpoint;
    }

}
