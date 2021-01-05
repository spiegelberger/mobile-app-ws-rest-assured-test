package com.spiegelberger.app.ws.restassuredtest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
 

class TestCreateUser {
	
	private final String CONTEXT_PATH = "/mobile-app-ws";

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI="http://localhost";
		RestAssured.port=8080;
	}

	@Test
	void testCreateUser() {
		
	     List<Map<String, Object>> userAddresses = new ArrayList<>();

	        Map<String, Object> shippingAddress = new HashMap<>();
	        shippingAddress.put("city", "City1");
	        shippingAddress.put("country", "Country1");
	        shippingAddress.put("streetName", "123 Street name");
	        shippingAddress.put("postalCode", "ABC456");
	        shippingAddress.put("type", "shipping");
	        
	        Map<String, Object> billingAddress = new HashMap<>();
	        billingAddress.put("city", "City1");
	        billingAddress.put("country", "Country1");
	        billingAddress.put("streetName", "123 Other Street name");
	        billingAddress.put("postalCode", "DEF456");
	        billingAddress.put("type", "billing");
	        
	        userAddresses.add(shippingAddress);
	        userAddresses.add(billingAddress);

	        Map<String, Object> userDetails = new HashMap<>();
	        userDetails.put("firstName", "Beno");
	        userDetails.put("lastName", "Bucko");
	        userDetails.put("email", "zsoltspiegelberger@gmail.com");
	        userDetails.put("password", "123");
	        userDetails.put("addresses", userAddresses);
		
		
		Response response = given()
		.contentType("application/json")
		.accept("application/json")
		.body(userDetails)
		.when()
		.post(CONTEXT_PATH + "/users")
		.then()
		.statusCode(200)
		.contentType("application/json")
		.extract()
		.response();
		
		String userId = response.jsonPath().get("userId");
		assertNotNull(userId);
	}

}
