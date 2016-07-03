package com.backbase.atmlocator.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestATMLocatorService {
	

	@Test
	public void test1() {
		executeAuthorizedTest("Amsterdam");
		/*executeAuthorizedTest("Rotterdam");
		executeAuthorizedTest("Den Haag");
		executeAuthorizedTest("Utrecht");
		executeAuthorizedTest("Eindhoven");*/
	}
	
	@Test
	public void test2() {
		executeUnAuthorizedTest("Rotterdam");
	}
	
	private void executeAuthorizedTest(String city){
		try{
			String uri = getURL(city);
			HttpGet getRequest = new HttpGet(uri);
			getRequest.addHeader("ATM_LOCATOR_USER", "backbase");
			getRequest.addHeader("ATM_LOCATOR_PWD", "backbase");
			getRequest.addHeader("ATM_LOCATOR_ROLE", "searchATM");
			
			
			HttpClientBuilder builder = HttpClientBuilder.create();
			HttpClient client = builder.build();
			HttpResponse response = client.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			assertEquals(200, statusCode);
			System.out.println("Status Code : " + statusCode);
			System.out.println("Output from Server .... \n");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void executeUnAuthorizedTest(String city){
		try{
			
			String uri = getURL(city);
			HttpGet getRequest = new HttpGet(uri);
			
			HttpClientBuilder builder = HttpClientBuilder.create();
			HttpClient client = builder.build();
			HttpResponse response = client.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("Status Code : " + statusCode);
			System.out.println("Output from Server .... \n");
			
			assertEquals(401, statusCode);
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			br.close();
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	private String getURL(String city) throws Exception{
		String baseURL = "http://localhost:8080/ATMLocator/services/searchATM/getATMByCity?city=";
		String uri = new StringBuilder().append(baseURL).append(URLEncoder.encode(city, "UTF-8")).toString();
		System.out.println("URL to test : " + uri);
		return uri;
	}
}
