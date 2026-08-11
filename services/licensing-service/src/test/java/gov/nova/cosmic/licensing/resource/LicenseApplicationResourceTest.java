package gov.nova.cosmic.licensing.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class LicenseApplicationResourceTest {

    @Test
    void testSubmitApplication() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                    "applicantFirstName": "Jane",
                    "applicantLastName": "Doe",
                    "applicantEmail": "jane.doe@example.com",
                    "licenseType": "DRIVERS_LICENSE"
                }
                """)
        .when()
            .post("/api/licensing/applications")
        .then()
            .statusCode(201)
            .body("applicantFirstName", equalTo("Jane"))
            .body("applicantLastName", equalTo("Doe"))
            .body("applicantEmail", equalTo("jane.doe@example.com"))
            .body("licenseType", equalTo("DRIVERS_LICENSE"))
            .body("status", equalTo("SUBMITTED"))
            .body("id", notNullValue());
    }

    @Test
    void testListApplications() {
        given()
        .when()
            .get("/api/licensing/applications")
        .then()
            .statusCode(200)
            .body("content", is(notNullValue()))
            .body("page", is(0))
            .body("size", is(20));
    }

    @Test
    void testGetApplicationById() {
        // First, create an application
        String id = given()
            .contentType(ContentType.JSON)
            .body("""
                {
                    "applicantFirstName": "John",
                    "applicantLastName": "Smith",
                    "applicantEmail": "john.smith@example.com",
                    "licenseType": "BUSINESS_LICENSE"
                }
                """)
        .when()
            .post("/api/licensing/applications")
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        // Then, retrieve it by ID
        given()
        .when()
            .get("/api/licensing/applications/{id}", id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("applicantFirstName", equalTo("John"))
            .body("applicantLastName", equalTo("Smith"))
            .body("licenseType", equalTo("BUSINESS_LICENSE"));
    }
}
