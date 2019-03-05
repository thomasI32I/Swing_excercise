import java.sql.SQLException;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class TestDatabase {

	public static void main(String[] args) {
		System.out.println("Running database test");
		
		Database db = null;
		try {
			db = new Database();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		db.addPerson(new Person("Hans",
				"Löwenbändiger",
				AgeCategory.ADULT,
				EmploymentCategory.EMPLOYED,
				true,
				"666",
				Gender.MALE));
		db.addPerson(new Person("Sue",
				"Pornodarstellerin",
				AgeCategory.SENIOR,
				EmploymentCategory.SELF_EMPLOYED,
				false,
				null,
				Gender.FEMALE));	
		
		try {
			db.save();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			db.load();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.disconnect();
	}
}
