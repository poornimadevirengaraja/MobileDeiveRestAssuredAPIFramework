package api.tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.github.javafaker.Faker;

import api.endpoints.endpoints;
import api.httpmethodsforusers.apimethodsoroperations;
import api.payloads.payloadgetsetpojo;
import api.utilities.extentReportUtilities;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class endtoendtest extends extentReportUtilities {

	payloadgetsetpojo payloadobject;
	Response response;

	@BeforeClass
	public void set_payload() {

		// Create a instance of object for payloadgetsetpojo class
		payloadobject = new payloadgetsetpojo();

		// setting values for request Payload
		payloadobject.setName("Apple Max Pro 1TB");
		payloadobject.setYear(2023);
		payloadobject.setPrice(7999.99f);
		payloadobject.setCpumodel("Apple ARM A7");
		payloadobject.setHarddisksize("1 TB");
	}

	@Test(priority = 1)
	public void TestCase1_addnewdevice_POST() {
		// The method used to create request body : LinkedHashMap is used to create request body in insertion order of name and data
		// Another method to create request body : you can save the response body in external JSONfile and Parse using JSONTokener and JSONObject to send requst body. 
		
		Map<String, Object> rb = new LinkedHashMap<String, Object>();
		rb.put("name", payloadobject.getName());

		// HashMap to create Object data
		Map<String, Object> dataobjrb = new HashMap<String, Object>();
		dataobjrb.put("year", payloadobject.getYear());
		dataobjrb.put("price", payloadobject.getPrice());
		dataobjrb.put("CPU model", payloadobject.getCpumodel());
		dataobjrb.put("Hard disk size", payloadobject.getHarddisksize());
		rb.put("data", dataobjrb);
		// System.out.println(rb);

		// POST method - To add a new device and check responses
		System.out.println("Testcase_1 - Post Method -  To add a new device and check responses");
		response = apimethodsoroperations.createuser_post(rb);
	}

	@Test(priority = 2)
	public void Testcase2_ValidateStatusCode() {
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test(priority = 3)
	public void Testcase3_ResponseBody_Validatename() {
		Assert.assertEquals(response.jsonPath().get("name").toString(), payloadobject.getName());
	}
	
	@Test(priority = 4)
	public void Testcase4_ResponseBody_Validateyear() {
		 // To check the data Object - year is received correctly - using jsonPath()
		 System.out.println(response.jsonPath().get("data.year").toString());
		 System.out.println(Integer.valueOf(response.jsonPath().get("data.year").toString()));
		 Assert.assertEquals(Integer.valueOf(response.jsonPath().get("data.year").toString()), payloadobject.getYear());
	}
	
	@Test(priority = 5)
	public void Testcase5_ResponseBody_Validateprice() {
		 // To check the data Object - price is received correctly - using jsonPath()
		  System.out.println(response.jsonPath().get("data.price").toString());
		  System.out.println(Float.valueOf(response.jsonPath().get("data.price").toString()));
		  Assert.assertEquals(Float.valueOf(response.jsonPath().get("data.price").toString()), payloadobject.getPrice());
	}

	

	@Test(priority = 6)
	public void Testcase6_ResponseBody_Validatecpumodel() {
		// To check the data Object - CPU Model is received correctly - with
		 JSONObject jsonobjcpu = new JSONObject(response.asString());
		 String cpumodl = jsonobjcpu.getJSONObject("data").get("CPU model").toString();
		 System.out.println(cpumodl);
		 Assert.assertEquals(cpumodl,payloadobject.getCpumodel());
	}

	@Test(priority = 7)
	public void Testcase7_ResponseBody_Validateharddisksize() {
		 // To check the data Object - Hard disk size is received correctly - with
		 JSONObject jsonobjhd = new JSONObject(response.asString()); String
		 hds = jsonobjhd.getJSONObject("data").get("Hard disk size").toString();
		 System.out.println(hds);
		 Assert.assertEquals(hds,payloadobject.getHarddisksize());
	}
	
	
	@Test(priority = 8)
	public void Testcase8_ResponseBody_ValidateidisNotNull() {
		// To validate id should not be null
		 Assert.assertNotNull(response.jsonPath().get("id").toString());
	}

	@Test(priority = 9)
	public void Testcase9_ResponseBody_ValidateCreateAtisNotNull() {
		 // To validate createAt should not be null
		 Assert.assertNotNull(response.jsonPath().get("createdAt").toString());
	}

	@Test(priority = 10)
	public void Testcase10_ResponseBody_ValidateSchema() {
		  // Place the mobiledeviceschema.json in project> target > classes folder	     
		  // Just For easy access to copy and paste json file and kept in api.utilites folder her
		    	MatcherAssert.assertThat(
		                "Validate json schema",
		                response.getBody().asString(),
		                JsonSchemaValidator.matchesJsonSchemaInClasspath("mobiledeviceschema.json"));
	}
	
}
