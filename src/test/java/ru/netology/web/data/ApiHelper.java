package ru.netology.web.data;

import static com.google.common.base.Predicates.equalTo;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;

public class ApiHelper {
    public static  void shouldReturnPage(Integer statusCod) {
             given()
                .baseUri("http://localhost:8080")
                .when()
                .get()
                .then()
                .statusCode(statusCod);
    }
    public static void testingPOSTRequestsCredit(DataHelper.CardInfo cardInfo, Integer statusCod, String status) {
        given()
                .baseUri("http://localhost:8080/api/v1")
                .contentType(ContentType.JSON)
                .body(cardInfo)
                .when()
                .post("/credit")
                .then()
                .statusCode(statusCod)
                .contentType(ContentType.JSON)
                .body("status", Matchers.is(status))
        ;
    }
    public static void testingPOSTRequestsPay(DataHelper.CardInfo cardInfo, Integer statusCod, String status) {
        given()
                .baseUri("http://localhost:8080/api/v1")
                .contentType(ContentType.JSON)
                .body(cardInfo)
                .when()
                .post("/pay")
                .then()
                .statusCode(statusCod)
                .contentType(ContentType.JSON)
                .body("status", Matchers.is(status))
        ;
    }
}
