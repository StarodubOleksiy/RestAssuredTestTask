package restapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sun.org.glassfish.gmbal.Description;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import restapi.*;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import ru.yandex.qatools.allure.annotations.*;

/**
 * Created by sbryt on 8/26/2016.
 */

/**
 * Just pure rest-assured tests
 */


public class TC001_GET_Request {

    ApplicationConfigReader reader = new ApplicationConfigReader();
    Response response;


    @BeforeTest
    public void suiteSetup() {
        RestAssured.baseURI = reader.getBaseURL();

        RequestSpecification httpRequest = RestAssured.given()
                .auth()
                .basic(reader.getLogin(), reader.getPassword());
        response = httpRequest.request(Method.GET, reader.getGuid());
    }

    @Test
    @TestCaseId("TC_Login_002")
    @Description("To verify incorrect phone number for login into Stone Edge Trading application")
    @Features("Stone Edge Trading login page")
    public void test1CorrectLogin() throws IOException {
        Assert.assertEquals(response.getStatusCode(), 200);//
    }


    @Test
    @TestCaseId("TC_Login_002")
    @Description("To verify incorrect phone number for login into Stone Edge Trading application")
    @Features("Stone Edge Trading login page")
    public void test2ForEndPoint() throws IOException {
        //Specify base URI
        XmlMapper mapper = new XmlMapper();
        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {
        };
        HashMap<String, Object> o = mapper.readValue(response.getBody().asString(), typeRef);

        int statusCode = response.getStatusCode();//
        Assert.assertEquals(statusCode, 200);//
        Assert.assertEquals(new Boolean(o.get("enabled").toString()).booleanValue(), true);
        Assert.assertEquals(OffsetDateTime.parse(o.get("created").toString()).getClass().getCanonicalName(), OffsetDateTime.class.getCanonicalName());
        Assert.assertEquals(o.get("product").getClass().getCanonicalName(), String.class.getCanonicalName());
        Assert.assertEquals(o.get("parent").getClass().getCanonicalName(), String.class.getCanonicalName());
        Assert.assertEquals(new Boolean(o.get("subscribed").toString()).booleanValue(), true);
    }





}
