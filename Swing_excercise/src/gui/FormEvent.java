package gui;

import java.util.EventObject;

import model.AgeCategory;

/**
 * 
 */
public class FormEvent extends EventObject {
	
	private String name;
	private String occupation;
	private int ageCategory;
	private String empCat;
	private boolean isUsCitizen;
	private String taxId;
	private String gender;

	/**
	 * Constructor
	 * 
	 * @param source
	 */
	public FormEvent(Object source) {
		super(source);
	}
	
	/**
	 * Constructor
	 * 
	 * @param source
	 * @param name
	 * @param occupation
	 * @param ageCategory
	 * @param empCat
	 * @param isUsCitizen
	 * @param taxId
	 * @param gender
	 */
	public FormEvent(Object source, String name, String occupation, int ageCategory,
			String empCat, boolean isUsCitizen, String taxId, String gender) {
		super(source);
		
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.empCat = empCat;
		this.isUsCitizen = isUsCitizen;
		this.taxId = taxId;
		this.gender = gender;
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

	public int getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(int ageCategory) {
		this.ageCategory = ageCategory;
	}

	public String getEmpCat() {
		return empCat;
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

	public String getGender() {
		return gender;
	}
	
}
	