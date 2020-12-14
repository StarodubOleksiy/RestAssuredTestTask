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
 * Created by Oleksiy on 14/12/2020.
 */

/**
 * Here is just some rest-assured tests
 */


public class TestResponseElemetsInXMLFormat {

    ApplicationConfigReader reader = new ApplicationConfigReader();// Creating instace of class that has important login data
    Response response; //Here is instance of response elements


    @BeforeTest
    public void signIn() {
        RestAssured.baseURI = reader.getBaseURL(); //read url to get rsponse elements
        RequestSpecification httpRequest = RestAssured.given()
                .auth()
                .basic(reader.getLogin(), reader.getPassword()); //Authorizate to this url using logi and password
        response = httpRequest.request(Method.GET, reader.getGuid()); //get response data using http method GET
    }

    @Test
    @TestCaseId("TC_Test_Response_Elements_001")
    @Description("Test to make sure that login is correct.")
    @Features("Response elements in XML format")
    public void test1CorrectLogin() throws IOException {
        Assert.assertEquals(response.getStatusCode(), 200);//Checking if sign in to get response elemets was successfull
    }


    @Test
    @TestCaseId("TC_Test_Response_Elements_002")
    @Description("Test to verify if all elements in the response are good.")
    @Features("Response elements in XML format")
    public void test2ForEndPoint() throws IOException {
        XmlMapper mapper = new XmlMapper();//Creating instance of class that parces elements in XML format
        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {
        }; //Creating instance of class that help extract response elements which are in XML format to hashmap container
        HashMap<String, Object> o = mapper.readValue(response.getBody().asString(), typeRef); //Extract response elements in XML format to hashmap container
        Assert.assertEquals(new Boolean(o.get("enabled").toString()).booleanValue(), true); //Verify if <<enabled>> element is Boolean type
        Assert.assertEquals(OffsetDateTime.parse(o.get("created").toString()).getClass().getCanonicalName(), OffsetDateTime.class.getCanonicalName()); //Verify if <<created>> element is iso8601-datetime type
        //If element <<created>> is not iso8601-datetime format it will be java.time.format.DateTimeParseException
        Assert.assertEquals(o.get("product").getClass().getCanonicalName(), String.class.getCanonicalName());  //Verify if <<product>> element is String type
        Assert.assertEquals(o.get("parent").getClass().getCanonicalName(), String.class.getCanonicalName());  //Verify if <<parent>> element is String type
        Assert.assertEquals(new Boolean(o.get("subscribed").toString()).booleanValue(), true);//Verify if <<subscribed>> element is Boolean type
    }


}
