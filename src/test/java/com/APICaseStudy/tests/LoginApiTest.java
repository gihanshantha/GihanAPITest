package com.APICaseStudy.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class LoginApiTest {

    private final String BASE_URL = "https://reqres.in/api/login";
    private final String API_KEY = "reqres-free-v1";

    @Test(priority = 1)
    public void testPositiveLogin() {
        String payload = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", API_KEY)
                .body(payload)
                .when()
                .post(BASE_URL);

        // Assertions
        assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        String token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
        assert token != null && !token.isEmpty();
    }

    @Test(priority = 2)
    public void testNegativeLogin_MissingPassword() {
        String payload = "{ \"email\": \"eve.holt@reqres.in\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", API_KEY)
                .body(payload)
                .when()
                .post(BASE_URL);

        // Assertions
        assertEquals(response.getStatusCode(), 400, "Expected status code 400");
        String error = response.jsonPath().getString("error");
        assertEquals(error, "Missing password");
    }

    @Test(priority = 3)
    public void testNegativeLogin_InvalidEmail() {
        String payload = "{ \"email\": \"test@gmail.com\", \"password\": \"test\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", API_KEY)
                .body(payload)
                .when()
                .post(BASE_URL);

        // Assertions
        assertEquals(response.getStatusCode(), 400, "Expected status code 400");
        String error = response.jsonPath().getString("error");
        assert error != null;
    }
}

