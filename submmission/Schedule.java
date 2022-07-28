/*
 * I declare that this code was written by me. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Ang Geok En
 * Student ID: 20047223
 * Class: E37C
 * Date/Time created: Thursday 21-01-2021 11:13
 */

public class Schedule {
	private int id;
	private String departureAirport;
	private String arrivalAirport;
	private String aircraft;
	private String flightDate;
	private String flightTime;
	private String status;
	
	public Schedule(int id, String departureAirport, String arrivalAirport, String aircraft, String flightDate,
			String flightTime, String status) {
		this.id = id;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.aircraft = aircraft;
		this.flightDate = flightDate;
		this.flightTime = flightTime;
		this.status = status;
		
		
	}

	public Schedule(int id, String departureAirport, String arrivalAirport, String aircraft, String flightDate,
			String flightTime) {
		this.id = id;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.aircraft = aircraft;
		this.flightDate = flightDate;
		this.flightTime = flightTime;
		this.status = "PENDING";
	}
	public int getId() {
		return id;
	}
	
	public String getDepartureAirport() {
		return departureAirport;
	}
	
	public String getArrivalAirport() {
		return arrivalAirport;
	}
	
	public String getAircraft() {
		return aircraft;
	}
	
	public String getFlightDate() {
		return flightDate;
	}
	public String getFlightTime() {
		return flightTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String newstat) {
		this.status = newstat; 
	}
	
}
