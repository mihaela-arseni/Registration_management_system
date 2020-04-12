package event;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static Scanner scanner = new Scanner(System.in);
	private static GuestsList guestList;

	public static void main(String[] args) throws IOException {
		guestList = readFromBinaryFile();
		
		String comand = "";

		if (guestList == null) {

//			Exception InputMismatch
			int numberOfSeats = 0;
			boolean isValidNumber = true;
			System.out.println("Welcome! Please enter the number of available seats.");

			while (isValidNumber) {
				try {
					numberOfSeats = scanner.nextInt();
					isValidNumber = false;
				} catch (InputMismatchException e) {
					scanner.nextLine();
					System.out.println("The input is invalid. Please try again.");
				}
			}

			guestList = new GuestsList(numberOfSeats);
			scanner.nextLine();

		}

		do {
			System.out.println("Waiting for the command: (help - Display the commands list)");

			comand = scanner.nextLine();
			switch (comand) {
			case "help":
				printComandsList();
				break;
			case "add":
				addGuestDetails();
				break;
			case "check":
				checkByCriteria();
				break;
			case "remove":
				removeParticipant();
				break;
			case "update":
				updateParticipant();
				break;
			case "guests":
				guestList.guests();
				break;
			case "waitlist":
				guestList.waitlist();
				break;
			case "available":
				guestList.available();
				break;
			case "guests_no":
				guestList.guests_no();
				break;
			case "waitlist_no":
				guestList.waitlist_no();
				break;
			case "subscribe_no":
				guestList.subscribe_no();
				break;
			case "search":
				searchParticipant();
				break;
			case "quit":
				System.out.println("Closing the application...");
				break;

			default:
				System.out.println("Error, the command is invalid.");
			}

		} while (!comand.equals("quit"));

		scanner.close();

		writeToBinaryFile(guestList);
	}
	
	public static void writeToBinaryFile(GuestsList list) throws IOException {
		try (ObjectOutputStream binaryFileOut = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("guests.dat")))) {
			binaryFileOut.writeObject(list);
			System.out.println("succes");
		}
	}

	public static GuestsList readFromBinaryFile() throws IOException {
		GuestsList data = null;

		try (ObjectInputStream binaryFileIn = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("guests.dat")))) {
			data = (GuestsList) binaryFileIn.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("The class was not found. Please contact the adminitrator.");
		} catch (FileNotFoundException f) {
			System.out.println("The file was not found. Please contact the adminitrator.");
			System.exit(1);
		} catch (EOFException e) {
			
		}

		return data;
	}

	public static void printComandsList() {
		System.out.println("help - Display the commands list");
		System.out.println("add - Add a new person (registration)");
		System.out.println("check - Check if a person is registered to the event");
		System.out.println("remove - Remove a person from the guests list");
		System.out.println("update - Update the guest's details");
		System.out.println("guests - Display the list of persons participating at the event");
		System.out.println("waitlist - Display the list of persons registered on the waitlist");
		System.out.println("available - Available places");
		System.out.println("guests_no - Number of persons participating at the event");
		System.out.println("waitlist_no - Number of persons from the waitlist");
		System.out.println("subscribe_no - Total number of registered persons");
		System.out.println("search - Search for a guest based on the entered string");
		System.out.println("quit - Close the application");
	}

	private static String getLastName() {
		System.out.println("Enter last name:");
		return scanner.nextLine();
	}

	private static String getFirstName() {
		System.out.println("Enter first name:");
		return scanner.nextLine();
	}

	private static String getEmail() {
		System.out.println("Enter email:");
		return scanner.nextLine();
	}

	private static String getPhoneNumber() {
		System.out.println("Enter the phone number (format \"+40733386463\"):");
		return scanner.nextLine();
	}

	private static void addGuestDetails() {
		System.out.println("Adding new participant...");
		String lastName = getLastName();
		String firstName = getFirstName();
		String[] fullName = { lastName, firstName };
		String email = getEmail();
		String phoneNumber = getPhoneNumber();
		Guest newGuest = new Guest(fullName[0], fullName[1], email, phoneNumber);
		int ret = guestList.add(newGuest);
		if (ret < 0) {
			System.out.println("Error. This person is already registered to the event.");
		}
	}

	public static int checkByCriteria() {

		String[] fullName = null;
		String email = null;
		String phoneNumber = null;
		boolean validCommand = true;

		do {
			System.out.println("Please choose the authentication mode, typing:");
			System.out.println("\"1\" - Last Name and First Name");
			System.out.println("\"2\" - Email");
			System.out.println("\"3\" - Phone Number");
			int option = scanner.nextInt();
			scanner.nextLine();
			validCommand = true;

			switch (option) {
			case 1:
				fullName = new String[] { getLastName(), getFirstName() };
				break;
			case 2:
				email = getEmail();
				break;
			case 3:
				phoneNumber = getPhoneNumber();
				break;
			default:
				validCommand = false;
				System.out.println("This option does not exist.");
				System.out.println("Please try again...");
			}
		} while (!validCommand);

		return guestList.findGuestPosition(fullName, email, phoneNumber);

	}

	private static void updateParticipant() {
		System.out.println("Updating the guest details...");
		int position = checkByCriteria();

		if (position < 0) {
			System.out.println("Error. This person is not registred at the event.");
			return;
		}

		updateByField(guestList.get(position));
	}

	private static void removeParticipant() {
		System.out.println("Removing a guest from the guests list...");
		int position = checkByCriteria();

		guestList.removeGuestByIndex(position);
	}

	private static void searchParticipant() {
		System.out.println("Searchig in the guests list, based on the entered string...");
		System.out.println("Enter the string, without spaces: ");
		String token = scanner.nextLine();
		ArrayList<Guest> matches = guestList.search(token);
		for (int i = 0; i < matches.size(); i++) {
			System.out.println(matches.get(i).describeGuest());
		}
	}

	private static boolean updateByField(Guest guest) {

		String lastName = null, firstName = null, email = null, phoneNumber = null;
		boolean validCommand;

		do {
			System.out.println("Please choose the field to be updated, typing:");
			System.out.println("\"1\" - Last Name");
			System.out.println("\"2\" - First Name");
			System.out.println("\"3\" - Email");
			System.out.println("\"4\" - Phone Number");
			int option = scanner.nextInt();
			scanner.nextLine();
			validCommand = true;

			switch (option) {
			case 1:
				lastName = getLastName();
				break;
			case 2:
				firstName = getFirstName();
				break;
			case 3:
				email = getEmail();
				break;
			case 4:
				phoneNumber = getPhoneNumber();
				break;
			default:
				validCommand = false;
				System.out.println("This option does not exist.");
				System.out.println("Please try again...");
			}
		} while (!validCommand);

		return guest.updateField(lastName, firstName, email, phoneNumber);
	}

}
