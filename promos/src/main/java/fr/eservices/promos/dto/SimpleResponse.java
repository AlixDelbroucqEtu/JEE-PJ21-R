package fr.eservices.promos.dto;

public class SimpleResponse {
	
	public String message;
	public Status status = Status.OK;
	
	public enum Status {
		OK, ERROR
	};
	

}
