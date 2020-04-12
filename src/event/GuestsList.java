package event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GuestsList implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int numberOfSeats;
	private List<Guest> listOfParticipants;
	private List<Guest> waitlist;

	public GuestsList(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
		this.listOfParticipants = new ArrayList<Guest>(this.numberOfSeats);
		this.waitlist = new ArrayList<Guest>();
	}
	
	

	private int findGuestByName(String[] fullName) {
		for (int i = 0; i < this.listOfParticipants.size(); i++) {
			if (this.listOfParticipants.get(i).isEqualByName(fullName[0], fullName[1])) {
				return i;
			}
		}
		return -1;
	}

	private int findGuestByEmail(String email) {
		for (int i = 0; i < this.listOfParticipants.size(); i++) {
			if (this.listOfParticipants.get(i).isEqualByEmail(email)) {
				return i;
			}
		}
		return -1;
	}

	private int findGuestByPhone(String phoneNumber) {
		for (int i = 0; i < this.listOfParticipants.size(); i++) {
			if (this.listOfParticipants.get(i).isEqualByPhone(phoneNumber)) {
				return i;
			}
		}
		return -1;
	}

	public int findGuestPosition(String[] fullName, String email, String phoneNumber) {

		if (fullName != null) {
			return findGuestByName(fullName);
		}
		if (email != null) {
			return findGuestByEmail(email);
		}
		if (phoneNumber != null) {
			return findGuestByPhone(phoneNumber);
		}
		System.out.println("Error. The method \"find guest\"couldn't be executed.");
		return -1;
	}

	private Guest find(Guest guest) {
		for (int i = 0; i < this.listOfParticipants.size(); i++) {
			if (this.listOfParticipants.get(i).isEqualTo(guest)) {
				return this.listOfParticipants.get(i);
			}
		}
		return null;
	}

	public int addFromFile(Guest guest) {

		Guest guest2 = find(guest);

		if (guest2 == null) {

			if (this.listOfParticipants.size() < this.numberOfSeats) {
				this.listOfParticipants.add(guest);
				return 0;
			} else if (this.listOfParticipants.size() >= this.numberOfSeats) {
				this.waitlist.add(guest);
				return this.waitlist.indexOf(guest);
			}
		}
		return -1;
	}

	public int add(Guest guest) {

		Guest guest1 = find(guest);

		if (guest1 == null) {

			if (this.listOfParticipants.size() < this.numberOfSeats) {
				System.out.println("Congratulations! Your place at the event is confirmed.");
				this.listOfParticipants.add(guest);
				return 0;
			} else if (this.listOfParticipants.size() >= this.numberOfSeats) {
				this.waitlist.add(guest);
				System.out.println("You have successfully signed up in the waitlist and received the order number "
						+ (this.waitlist.indexOf(guest) + 1)
						+ " . We will notify you once we will have an available place.");
				return this.waitlist.indexOf(guest);
			}
		} else {
			System.out.println("You are already registered at the event.");
		}
		return -1;

	}

	// Exception IndexOutOfBounds
	public boolean removeGuestByIndex(int index) {

		try {
			if (index < this.numberOfSeats) {
				this.listOfParticipants.remove(index);

				if (!this.waitlist.isEmpty()) {
					System.out.println(
							"[" + this.waitlist.get(0).getFirstName() + " " + this.waitlist.get(0).getLastName()
									+ "] \"Congratulations! Your place at the event is confirmed.\"");
					this.listOfParticipants.add(this.waitlist.remove(0));
				}
				System.out.println("We have removed the person from the guests list.");
				return true;
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error. The method \"remove guest\" couldn't be executed.");
		}
		return false;
	}

	public void guests() {
		if (this.listOfParticipants.isEmpty()) {
			System.out.println("The guests list is empty...");
			return;
		}

		for (int i = 0; i < this.listOfParticipants.size(); i++) {
			System.out.println(this.listOfParticipants.get(i));
		}
	}

	public void waitlist() {
		if (this.waitlist.isEmpty()) {
			System.out.println("The waitlist is empty...");
			return;
		}

		for (int i = 0; i < this.waitlist.size(); i++) {
			System.out.println(this.waitlist.get(i));
		}
	}

	public void available() {
		System.out.println(this.numberOfSeats - this.listOfParticipants.size());
	}

	public void guests_no() {
		System.out.println("The total number of participants: " + this.listOfParticipants.size());
	}

	public void waitlist_no() {
		System.out.println("The waitlist dimension is " + this.waitlist.size());
	}

	public void subscribe_no() {
		System.out
				.print("The total number of persons: " + (this.waitlist.size() + this.listOfParticipants.size()) + " ");
	}

	public ArrayList<Guest> search(String keyWord) {

		ArrayList<Guest> listContainKeyWord = new ArrayList<Guest>();

		for (int i = 0; i < this.listOfParticipants.size(); i++) {
			if (this.listOfParticipants.get(i).searchInEachField(keyWord)) {
				listContainKeyWord.add(this.listOfParticipants.get(i));
			}
		}
		return listContainKeyWord;
	}

	public Guest get(int index) {
		if (index < 0 || index >= this.listOfParticipants.size()) {
			System.out.println("Error: Index out of bounds.");
			return null;
		}

		return listOfParticipants.get(index);
	}

	public List<Guest> getListOfParticipants() {
		return listOfParticipants;
	}

	public void setListOfParticipants(List<Guest> list) {
		this.listOfParticipants = list;
	}

	public List<Guest> getWaitlist() {
		return waitlist;
	}

	public void setWaitlist(ArrayList<Guest> waitlist) {
		this.waitlist = waitlist;
	}

	@Override
	public String toString() {
		return "GuestsList [numberOfSeats=" + numberOfSeats + ", \n listOfParticipants=" + listOfParticipants
				+ ", \n waitlist=" + waitlist + "]";
	}

}
