package model;

import java.io.Serializable;

public class Person implements Serializable {
	// usage for automatic id generation
	private static int count = 1;

	private int id;
	private String name;
	private String occupation;
	private AgeCategory ageCategory;
	private EmploymentCategory empCat;
	private boolean isUsCitizen;
	private String taxId;
	private Gender gender;

	/**
	 * 
	 * @param name
	 * @param occupation
	 * @param ageCategory
	 * @param empCat
	 * @param isUsCitizen
	 * @param taxId
	 * @param gender
	 */
	public Person(String name, String occupation, AgeCategory ageCategory, EmploymentCategory empCat,
			boolean isUsCitizen, String taxId, Gender gender) {
		this.id = count;
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.empCat = empCat;
		this.isUsCitizen = isUsCitizen;
		this.taxId = taxId;
		this.gender = gender;

		count++;
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param occupation
	 * @param ageCategory
	 * @param empCat
	 * @param isUsCitizen
	 * @param taxId
	 * @param gender
	 */
	public Person(int id,
				  String name,
				  String occupation,
				  AgeCategory ageCategory,
				  EmploymentCategory empCat,
				  boolean isUsCitizen,
				  String taxId,
				  Gender gender) {
		this(name, occupation, ageCategory, empCat, isUsCitizen, taxId, gender);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public EmploymentCategory getEmpCat() {
		return empCat;
	}

	public void setEmpCat(EmploymentCategory empCat) {
		this.empCat = empCat;
	}

	public boolean isUsCitizen() {
		return isUsCitizen;
	}

	public void setUsCitizen(boolean isUsCitizen) {
		this.isUsCitizen = isUsCitizen;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String toString() {
		return id + ": " + name;
	}

}
