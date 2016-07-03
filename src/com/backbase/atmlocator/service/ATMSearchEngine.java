package com.backbase.atmlocator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.backbase.atmlocator.model.ATM;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


public class ATMSearchEngine {
	
	
	private String dataProviderURL;
	
	
	public List<ATM> getATMByCity(String cityName) throws Exception{
		System.out.println("Search ATM for " + cityName);
		List<ATM> atmList = new ArrayList<ATM>();
		RestTemplate restTemplate = new RestTemplate();
		
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(dataProviderURL, String.class);
		String response = responseEntity.getBody();
		response = response.substring(5); // the JSON string returned from ING service has error
		
		ObjectMapper objMapper = new ObjectMapper();
		List<ATM> atms = objMapper.readValue(response, TypeFactory.defaultInstance().constructCollectionType(List.class, ATM.class));
		System.out.println("Total ING ATM found : " + atms.size());
		for (ATM atm : atms){
			if (null != cityName && cityName.equalsIgnoreCase(atm.getAddress().getCity())){
				atmList.add(atm);
			}
		}
		System.out.println("Total ATM found for " + cityName.toUpperCase() + " : " + atmList.size());
		return atmList;
	}


	public String getDataProviderURL() {
		return dataProviderURL;
	}


	public void setDataProviderURL(String dataProviderURL) {
		this.dataProviderURL = dataProviderURL;
	}
	
	
}
