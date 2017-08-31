package com.ball.lxvi.model;

public class Product {
	
	enum COUNTRY{
		THAI,
		JAPAN,
		ITALY,
		USA,
		ENGLAND
	}

	private long id;
	private String code;
	private String name;
	private COUNTRY country;
	private double price;
	public long getId() {
		return id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public COUNTRY getCountry() {
		return country;
	}
	public void setCountry(COUNTRY country) {
		this.country = country;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
