import java.sql.Timestamp;

/*
 * Mappiamo l'entità Actor del DB:
 * vediamo quali campi ha tale entità e riproduciamoli
 * in modo possibilmente fedele. Aggiungiamo getters, setters e toString.
 */

public class Actor {

	private Integer id;
	private String firstName;
	private String lastName;
	private Timestamp lastUpdate;

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return onlyNonNullFieldsToString();
	}

	private String onlyNonNullFieldsToString() {
		String toPrint = "";
		if (id != null && id != 0) {
			toPrint += "\nActor id: " + id;
		}
		if (firstName != null || firstName.length() != 0) {
			toPrint += "\nFirst name: " + firstName;
		}
		if (lastName != null || lastName.length() != 0) {
			toPrint += "\nLast name: " + lastName;
		}
		if (lastUpdate != null) {
			toPrint += "\nLast update: " + lastUpdate;
		}
		return toPrint;
	}

//	@Override
//	public String toString() {
//		return "Actor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate=" + lastUpdate
//				+ "]";
//	}
}
