package rest.assured.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

public class BaseTest {

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";

        RestAssured.requestSpecification = new RequestSpecBuilder().
                    setContentType(ContentType.JSON).
                    build();

    }




}
