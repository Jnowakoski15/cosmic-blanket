package gov.nova.cosmic.vitalrecords.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class CertificateRequestResourceTest {

    @Test
    void testSubmitRequest() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                    "requesterFirstName": "Jane",
                    "requesterLastName": "Doe",
                    "requesterEmail": "jane.doe@example.com",
                    "certificateType": "BIRTH_CERTIFICATE",
                    "subjectFirstName": "Baby",
                    "subjectLastName": "Doe",
                    "subjectDateOfBirth": "2024-01-15"
                }
                """)
        .when()
            .post("/api/vital-records/requests")
        .then()
            .statusCode(201)
            .body("requesterFirstName", equalTo("Jane"))
            .body("requesterLastName", equalTo("Doe"))
            .body("requesterEmail", equalTo("jane.doe@example.com"))
            .body("certificateType", equalTo("BIRTH_CERTIFICATE"))
            .body("status", equalTo("SUBMITTED"))
            .body("trackingNumber", startsWith("VR-"))
            .body("id", notNullValue());
    }

    @Test
    void testListRequests() {
        given()
        .when()
            .get("/api/vital-records/requests")
        .then()
            .statusCode(200)
            .body("content", is(notNullValue()))
            .body("page", is(0))
            .body("size", is(20));
    }

    @Test
    void testGetRequestById() {
        String id = given()
            .contentType(ContentType.JSON)
            .body("""
                {
                    "requesterFirstName": "John",
                    "requesterLastName": "Smith",
                    "requesterEmail": "john.smith@example.com",
                    "certificateType": "DEATH_CERTIFICATE",
                    "subjectFirstName": "James",
                    "subjectLastName": "Smith",
                    "subjectDateOfBirth": "1950-03-20",
                    "subjectDateOfDeath": "2024-01-10"
                }
                """)
        .when()
            .post("/api/vital-records/requests")
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
        .when()
            .get("/api/vital-records/requests/{id}", id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("requesterFirstName", equalTo("John"))
            .body("requesterLastName", equalTo("Smith"))
            .body("certificateType", equalTo("DEATH_CERTIFICATE"));
    }
}
