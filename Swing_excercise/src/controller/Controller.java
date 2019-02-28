package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gui.FormEvent;
import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

/**
 * Accepts input and converts it to commands for the model or view.
 *
 */
public class Controller {

	private Database db = new Database();

	public List<Person> getPeople() {
		return db.getPeople();
	}

	/**
	 * Transforms data from an formEvent to a person object and adds it to db.
	 */
	public void addPerson(FormEvent e) {

		String name = e.getName();
		String occupation = e.getOccupation();
		int age = e.getAgeCategory();
		String empCat = e.getEmpCat();
		boolean isUsCitizen = e.isUsCitizen();
		String taxId = e.getTaxId();
		String gender = e.getGender();

		AgeCategory ageCategory = null;
		switch (age) {
		case 0:
			ageCategory = AgeCategory.CHILD;
			break;
		case 1:
			ageCategory = AgeCategory.ADULT;
			break;
		case 2:
			ageCategory = AgeCategory.SENIOR;
			break;
		}

		EmploymentCategory employmentCat;
		switch (empCat) {
		case "employed":
			employmentCat = EmploymentCategory.EMPLOYED;
			break;
		case "self-employed":
			employmentCat = EmploymentCategory.SELF_EMPLOYED;
			break;
		case "unemployed":
			employmentCat = EmploymentCategory.UNEMPLOYED;
			break;
		default:
			employmentCat = EmploymentCategory.OTHER;
			break;
		}

		Gender genderCat;
		if (gender.equals("male")) {
			genderCat = Gender.MALE;
		} else {
			genderCat = Gender.FEMALE;
		}

		Person person = new Person(name, occupation, ageCategory, employmentCat, isUsCitizen, taxId, genderCat);
		db.addPerson(person);
	}

	public void removePerson(int row) {
		db.removePerson(row);
	}

	/**
	 * @throws IOException
	 * 
	 */
	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}

	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}

}
