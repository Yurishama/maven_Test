import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Base64;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;

public class restApi1 {


    @Test
    public void t01_get_token_fail(){
        //Request an account token without authorization header
        RestAssured.baseURI = String.format("https://webapi.segundamano.mx/nga/api/v1.1/private/accounts");
        Response response = given().log().all()
                .post();
        //validations
        System.out.println("Status expected: 400" );
        System.out.println("Result: " + response.getStatusCode());
        assertEquals(400,response.getStatusCode());
        String errorCode = response.jsonPath().getString("error.code");
        System.out.println("Error Code expected: VALIDATION FAILED \nResult: " + errorCode);
        assertEquals("VALIDATION_FAILED",errorCode);
    }

    @Test
    public void Get_Token(){

        String email = "apitest@mailinator.com";
        String pass = "12345";
        String ToEncode = email + ":" + pass;
        String Basic_encoded = Base64.getEncoder().encodeToString(ToEncode.getBytes());


        RestAssured.baseURI = String.format("https://webapi.segundamano.mx/nga/api/v1.1/private/accounts");
        Response response = given().queryParam("lang","es").log().all()
                .header("Authorization","Basic "+ Basic_encoded)
                .post();


        String body = response.getBody().asString();
        System.out.println("Response body: " + response.getBody().asString());


        assertEquals(200,response.getStatusCode());
        assertNotNull(body);
        assertTrue(body.contains("access_token"));

    }
}
