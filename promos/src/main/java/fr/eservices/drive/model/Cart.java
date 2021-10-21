package fr.eservices.drive.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart {

	List<CartElement> elements = new ArrayList<>();

	public List<CartElement> getElements() {
		return elements;
	}

	public void setElements(List<CartElement> elements) {
		this.elements = elements;
	}
	
	
	
}
