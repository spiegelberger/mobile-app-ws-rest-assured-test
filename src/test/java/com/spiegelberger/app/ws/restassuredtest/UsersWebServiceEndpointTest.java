package com.spiegelberger.app.ws.restassuredtest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UsersWebServiceEndpointTest {
	
	private final String CONTEXT_PATH = "/mobile-app-ws";
	private final String EMAIL="zsoltspiegelberger@gmail.com";
	private final String JSON = "application/json";
	private static String authorizationHeader;
	private static String userId;
	private static List<Map<String, String>> addresses;

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI="http://localhost";
		RestAssured.port=8080;
	}

	
	/*
	 * testUserLogin()
	 */
	@Test
	void a() {
				
		Map<String, String>loginDetails= new HashMap<>();
		loginDetails.put("email", EMAIL);
		loginDetails.put("password", "123");
		
		Response response = given()
		.contentType(JSON)
		.accept(JSON)
		.body(loginDetails)
		.when().post(CONTEXT_PATH + "/users/login")
		.then()
		.statusCode(200).extract().response();
		
		authorizationHeader = response.header("Authorization");
		userId = response.header("UserID");
		
		assertNotNull(authorizationHeader);
		assertNotNull(userId);
				
	}
	
	
	
	/*
	 * testGetUserDetails()
	 */
/*	@Test
	final void b() {
		
		Response response = given()
				 .pathParam("id", userId)
				 .header("Authorization",authorizationHeader)
				 .accept(JSON)
				 .when()
				 .get(CONTEXT_PATH + "/users/{id}")
				 .then()
				 .statusCode(200)
				 .contentType(JSON)
				 .extract()
				 .response();
				
				String userPublicId = response.jsonPath().getString("userId");
				String userEmail = response.jsonPath().getString("email");
		        String firstName = response.jsonPath().getString("firstName");
		        String lastName = response.jsonPath().getString("lastName");
		        addresses = response.jsonPath().getList("addresses");
		        String addressId = addresses.get(0).get("addressId");
				
				assertNotNull(userPublicId);
				assertNotNull(userEmail);
				assertNotNull(firstName);
				assertNotNull(lastName);
				assertEquals(EMAIL, userEmail);
				
				assertTrue(addresses.size() == 2);
				assertTrue(addressId.length() == 30);
	}
*/
	
	
	/*
	 * test UpdateUser()
	 */
	@Test
	final void c() {
		
		Map<String, String>userDetails=new HashMap<>();
        userDetails.put("firstName", "Beno1");
        userDetails.put("lastName", "Bucko1");
		
		Response response = given()
				 .header("Authorization",authorizationHeader)
				 .contentType(JSON)
				 .accept(JSON)
				 .pathParam("id", userId)
				 .body(userDetails)
				 .when()
				 .put(CONTEXT_PATH + "/users/{id}")
				 .then()
				 .statusCode(200)
				 .contentType(JSON)
				 .extract()
				 .response();
				
        String firstName = response.jsonPath().getString("firstName");
        String lastName = response.jsonPath().getString("lastName");
        List<Map<String, String>>storedAddresses = response.jsonPath().getList("addresses");
        
        assertEquals("Beno1", firstName);
        assertEquals("Bucko1", lastName);
        assertNotNull(storedAddresses);
        assertTrue(storedAddresses.size()==2);
        assertEquals(addresses.get(0).get("streetName"),storedAddresses.get(0).get("streetName"));
        
		
	}
	
}
