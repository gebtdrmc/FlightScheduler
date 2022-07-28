/*
 * I declare that this code was written by me. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Ang Geok En
 * Student ID: 20047223
 * Class: E37C
 * Date/Time created: Thursday 21-01-2021 11:42
 */

public class Staff {
	private String name;
	private String username;
	private String password;
	private int staffid;
	private String role;
	
	public Staff(String name, String username, String password, int staffid, String role) {

		this.name = name;
		this.username = username;
		this.password = password;
		this.staffid = staffid;
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getStaffId() {
		return staffid;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setPassword(String newpass) {
		this.password = newpass;
	}
}
