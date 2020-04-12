package event;

import java.io.Serializable;

public class Guest implements Serializable {

	private long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;

	public Guest(String firstName, String lastName, String email, String phoneNumber) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public boolean isEqualTo(Guest g) {
		return email.equalsIgnoreCase(g.email);
	}

	public boolean isEqualByName(String lastName, String firstName) {
		return lastName.equalsIgnoreCase(this.lastName) && firstName.equalsIgnoreCase(this.firstName);
	}

	public boolean isEqualByEmail(String email) {
		return this.email.equalsIgnoreCase(email);
	}

	public boolean isEqualByPhone(String phoneNumber) {
		return this.phoneNumber.equalsIgnoreCase(phoneNumber);
	}

	public boolean updateField(String lastName, String firstName, String email, String phoneNumber) {
		if (lastName != null) {
			this.lastName = lastName;
			return true;
		}
		if (firstName != null) {
			this.firstName = firstName;
			return true;
		}
		if (email != null) {
			this.email = email;
			return true;
		}
		if (phoneNumber != null) {
			this.phoneNumber = phoneNumber;
			return true;
		}

		System.out.println("Error. The field couldn't be updated.");
		return false;
	}

	public boolean searchInEachField(String key) {
		key = key.toLowerCase();
		return this.lastName.toLowerCase().contains(key) || this.firstName.toLowerCase().contains(key)
				|| this.email.toLowerCase().contains(key) || this.phoneNumber.toLowerCase().contains(key);
	}

	public String describeGuest() {
		return "Last name: " + this.lastName + ", First name: " + this.firstName + ", Email: " + this.email
				+ ", Phone number: " + this.phoneNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Guest [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber="
				+ phoneNumber + "]";
	}

}
