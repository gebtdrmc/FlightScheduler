/*
*
* I declare that this code was written by me.
* I will not copy or allow others to copy my code.
* I understand that copying code is considered as plagiarism.
*
* Student Name: Ang Geok En
* Student ID: 20047223
* Class:E37C
* Date/Time Last modified: 03/02/2021 17:05
*
*/

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

/* START: DO NOT change this part of the code */
public class FlightScheduler {

	public static void main(String[] args) {

		Schedule schedule1 = new Schedule(101, "SIN - Changi", "BKK - Suvarnabhumi", "Airbus A330-300", "22/01/2021",
				"14:45", "CONFIRMED");
		Schedule schedule2 = new Schedule(102, "BKK - Suvarnabhumi", "SIN - Changi", "Boeing 777-300", "24/01/2021",
				"12:30", "CONFIRMED");
		Schedule schedule3 = new Schedule(103, "KUL - KLIA", "BKK - Suvarnabhumi", "Boeing 777-300", "18/01/2021",
				"12:30", "CONFIRMED");

		ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
		scheduleList.add(schedule1);
		scheduleList.add(schedule2);
		scheduleList.add(schedule3);

		Staff staff = new Staff("Stefan Chan", "stefan_chan", "654321", 5, "admin");

		while (true) {

			FlightScheduler.loginMenu();
			String uName = Helper.readString("Enter username > ");
			String uPassword = Helper.readString("Enter password > ");

			boolean isStaff = FlightScheduler.doStaffLogin(staff, uName, uPassword);

			if (isStaff == false) {
				System.out.println("Either your username or password was incorrect. Please try again!");
			}


			while (isStaff) {
				FlightScheduler.staffMenu();
				int staffChoice = Helper.readInt("Enter choice > ");

				if (staffChoice == 1) { 

					String allFlightSchedules = FlightScheduler.scheduleListToString(scheduleList);
					System.out.println(allFlightSchedules);
					System.out
							.println("Total number of flights: " + FlightScheduler.getNumFlightSchedules(scheduleList));

				} else if (staffChoice == 2) {

					int scheduleId = Helper.readInt("Enter flight schedule ID > ");
					String departAP = Helper.readString("Enter departure airport > ");
					String arriveAP = Helper.readString("Enter arrival airport > ");
					String aircraft = Helper.readString("Enter aircraft > ");
					String flightDate = Helper.readString("Enter flight date (dd/mm/yyyy) > ");
					String flightTime = Helper.readString("Enter flight time (hh:mm) > ");

					Schedule newSchedule = new Schedule(scheduleId, departAP, arriveAP, aircraft, flightDate,
							flightTime);

					boolean result = FlightScheduler.addFlightSchedule(scheduleList, newSchedule);

					if (result == true) {
						System.out.println("Flight schedule added!");
					} else {
						System.out.println("Flight schedule NOT added, you must include all details!");
					}

				} else if (staffChoice == 3) { 

					int editFlightID = Helper.readInt("Enter Flight Schedule ID > ");

					String flightScheduledetails = FlightScheduler.getFlightScheduleById(scheduleList, editFlightID);

					if (!flightScheduledetails.isEmpty()) {
						System.out.println(flightScheduledetails);
						String statusUpdate = Helper.readString("Enter new schedule status > ");
						boolean isEdited = FlightScheduler.editFlightScheduleStatus(scheduleList, editFlightID,
								statusUpdate);

						if (isEdited) {
							flightScheduledetails = FlightScheduler.getFlightScheduleById(scheduleList, editFlightID);
							System.out.println(String.format("Flight schedule status %d updated to %s", editFlightID,
									statusUpdate.toUpperCase()));
							System.out.println(flightScheduledetails);
						} else {
							System.out.println("The status you entered is invalid.");
						}
					} else {
						System.out.println("That Flight Schedule ID does not exist!");
					}

				} else if (staffChoice == 4) {

					int deleteFlightID = Helper.readInt("Enter Flight Schedule ID > ");

					String flightScheduledetails = FlightScheduler.getFlightScheduleById(scheduleList, deleteFlightID);

					if (!flightScheduledetails.isEmpty()) {
						System.out.println(flightScheduledetails);
						char toDelete = Helper.readChar("Do you wish to delete this flight?(y/n) > ");

						if (toDelete == 'y') {
							boolean deleted = FlightScheduler.removeFlightSchedule(scheduleList, deleteFlightID);

							if (deleted == true) {
								System.out.println(String.format("Flight schedule id %d was deleted successfully.",
										deleteFlightID));
							} else {
								System.out.println("Something went wrong, flight schedule was not deleted.");
							}
						}

					} else {
						System.out.println("That Flight Schedule ID does not exist!");
					}

				} else if (staffChoice == 5) {
					String airportSearch = Helper.readString("Enter airport to search > ");
					ArrayList<Schedule> resultList = getFlightSchedulesByAirport(scheduleList, airportSearch);

					if (!resultList.isEmpty()) {
						System.out.println(FlightScheduler.scheduleListToString(resultList));
					} else {
						System.out.println("No flight schedules with that airport.");
					}

				} else if (staffChoice == 6) {

					char resetPw = Helper.readChar("Are you sure you want to reset your password? (y/n) > ");

					if (resetPw == 'y') {
						staff = FlightScheduler.resetPassword(staff);
						System.out.println("Password reset successful.");
						System.out.println("Your new password is: " + staff.getPassword());
					}

				} else if (staffChoice == 7) {
					isStaff = false;
					System.out.println("Goodbye!");

				} else {
					System.out.println("Invalid choice");
				}

			}

		}

	}

	public static void loginMenu() {
		Helper.line(30, "-");
		System.out.println("FLIGHT SCHEDULER - LOGIN");
		Helper.line(30, "-");
	}

	public static void staffMenu() {

		Helper.line(30, "-");
		System.out.println("FLIGHT SCHEDULER - STAFF");
		Helper.line(30, "-");

		System.out.println("1. View all flight schedules");
		System.out.println("2. Add a new flight schedule");
		System.out.println("3. Update a flight schedule status");
		System.out.println("4. Remove a flight schedule");
		System.out.println("5. Search for flight schedules by airport");
		System.out.println("6. Reset password");
		System.out.println("7. Log out");

	}

	public static int getNumFlightSchedules(ArrayList<Schedule> scheduleList) {

		return scheduleList.size();

	}

	public static String getFlightScheduleById(ArrayList<Schedule> scheduleList, int flightScheduleId) {

		String output = "";

		for (int i = 0; i < scheduleList.size(); i++) {
			Schedule s = scheduleList.get(i);

			if (s.getId() == flightScheduleId) {
				output += String.format("%-3s %-20s %-20s %-15s %-15s %-20s %-10s\n", "ID", "DEPARTURE", "ARRIVAL",
						"FLIGHT DATE", "FLIGHT TIME", "AIRCRAFT", "STATUS");
				output += String.format("%-3d %-20s %-20s %-15s %-15s %-20s %-10s\n", s.getId(),
						s.getDepartureAirport(), s.getArrivalAirport(), s.getFlightDate(), s.getFlightTime(),
						s.getAircraft(), s.getStatus());
				break;
			}
		}

		return output;
	}

/* END: DO NOT change this part of the code */
	
	
	
	
/*
 *   Refer to the assignment document for the specification of the methods below.
 */

	public static boolean doStaffLogin(Staff staff, String uName, String uPassword) {

		// Complete code here
		boolean loginCom = true;
		if(!uName.equals(staff.getName()) && !uPassword.equals(staff.getPassword())) {
			loginCom = false;
		} else {
			loginCom = true;
		}
		return loginCom;
	}

	public static ArrayList<Schedule> getFlightSchedulesByAirport(ArrayList<Schedule> scheduleList, String airport) {

		// Complete code here
		ArrayList<Schedule> airportList = new ArrayList<Schedule>();
		
		for(int i=0;i<scheduleList.size();i++) {
			if(scheduleList.get(i).getDepartureAirport().toLowerCase().contains(airport) || scheduleList.get(i).getArrivalAirport().toLowerCase().contains(airport)) {
				Schedule airports = new Schedule(scheduleList.get(i).getId(), scheduleList.get(i).getDepartureAirport(), scheduleList.get(i).getArrivalAirport(), scheduleList.get(i).getFlightDate(), scheduleList.get(i).getFlightTime(), scheduleList.get(i).getAircraft(), scheduleList.get(i).getStatus());
				airportList.add(airports);
				
			}
		}
		return airportList;
	}

	public static String scheduleListToString(ArrayList<Schedule> scheduleList) {

		// Complete code here
		String output = String.format("%-5s %-20s %-20s %-20s %-20s %-20s %-10s", "ID", "DEPARTURE", "ARRIVAL", "FLIGHT DATE", "FLIGHT TIME", "AIRCRAFT", "STATUS");
		for(int i=0;i<scheduleList.size();i++) {
			output += String.format("\n%-5s %-20s %-20s %-20s %-20s %-20s %-10s", scheduleList.get(i).getId(), scheduleList.get(i).getDepartureAirport(), scheduleList.get(i).getArrivalAirport(), scheduleList.get(i).getFlightDate(), scheduleList.get(i).getFlightTime(), scheduleList.get(i).getAircraft(), scheduleList.get(i).getStatus());
		}
		return output;
	}

	public static boolean addFlightSchedule(ArrayList<Schedule> scheduleList, Schedule schedule) {

		// Complete code here
		boolean ifempty = false;
		if(schedule.getDepartureAirport().isEmpty() || schedule.getArrivalAirport().isEmpty() || schedule.getFlightDate().isEmpty() || schedule.getFlightTime().isEmpty() || schedule.getAircraft().isEmpty()){
			ifempty = false;
		} else {
			ifempty = true;
			scheduleList.add(schedule);
		}
		return ifempty;
	}

	public static boolean editFlightScheduleStatus(ArrayList<Schedule> scheduleList, int scheduleId, String newStatus) {

		// Complete code here
		boolean isupdated = false;
		for (int i=0;i<scheduleList.size();i++) {
			if(scheduleId == scheduleList.get(i).getId()) {
				isupdated = true;
				scheduleList.get(i).setStatus(newStatus);
			} else {
				isupdated = false;
			}
				
		}
		return isupdated;
	}

	public static boolean removeFlightSchedule(ArrayList<Schedule> scheduleList, int scheduleId) {

		// Complete code here
		boolean isremoved = false;
		for (int i=0;i<scheduleList.size();i++) {
			if(scheduleId == scheduleList.get(i).getId()) {
				isremoved = true;
				scheduleList.remove(i);
			} else {
				isremoved = false;
			}
		}
		return isremoved;
	}

	public static Staff resetPassword(Staff staff) {

		// Complete code here
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String newpassword = "";
		for(int i=0;i<8;i++) {
			Random random = new Random();
			int result = random.nextInt(letters.length()) + 1;
			newpassword += letters.charAt(result);
		}
		staff.setPassword(newpassword);
		return staff;
	}

}
