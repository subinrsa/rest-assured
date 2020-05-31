package rest.assured.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
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

        RestAssured.responseSpecification = new ResponseSpecBuilder().
                    expectContentType(ContentType.JSON).
                 // expectStatusCode(HttpStatus.SC_OK).
                    build();


    }




}
