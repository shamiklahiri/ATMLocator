package com.backbase.atmlocator.model;

import java.io.Serializable;

public class ATM implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private ATMAddress address;
	
	private int distance;
	
	private String type;

	public ATMAddress getAddress() {
		return address;
	}

	public void setAddress(ATMAddress address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	} 

	
}
