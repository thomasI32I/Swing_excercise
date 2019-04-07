package model;

public enum EmploymentCategory {
	
	EMPLOYED("employed"),
	SELF_EMPLOYED("self employed"),
	UNEMPLOYED("unemployed"),
	OTHER("other");
	
	private String text;
	
	private EmploymentCategory(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
	
	
}
