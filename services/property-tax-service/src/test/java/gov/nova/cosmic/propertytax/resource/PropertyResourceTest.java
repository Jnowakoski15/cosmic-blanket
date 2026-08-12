package gov.nova.cosmic.propertytax.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class PropertyResourceTest {

    @Test
    void testListProperties() {
        given()
        .when()
            .get("/api/property-tax/properties")
        .then()
            .statusCode(200)
            .body("content", is(notNullValue()))
            .body("page", is(0))
            .body("size", is(20));
    }

    @Test
    void testSearchProperties() {
        given()
        .when()
            .get("/api/property-tax/properties/search?address=Main")
        .then()
            .statusCode(200);
    }

    @Test
    void testGetPropertyNotFound() {
        given()
        .when()
            .get("/api/property-tax/properties/{parcelNumber}", "NONEXISTENT-123")
        .then()
            .statusCode(404);
    }
}
