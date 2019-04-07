package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 *
 */
public class Database {
	
	private final static String DB_SERVER = "localhost";
	private final static String DB_NAME = "swingtest";
	
//	private final static String DB_SERVER_PORT = "3306";
//	private final static String ROOT_USER = "root";
//	private final static String ROOT_USER_PW = "Tomislaw33!!!";
	

	private LinkedList<Person> people;
	private Connection connection;
	
	private int port;
	private String user;
	private String password;

	/**
	 * Constructor
	 * 
	 */
	public Database() {
		people = new LinkedList<>();
	}
	
	public void configure(int port, String user, String password) throws SQLException {
		this.port = port;
		this.user = user;
		this.password = password;
		
		if (connection != null) {
			disconnect();
			connect();
		}
	}
	

	/**
	 * 
	 * @throws Exception
	 */
	public void connect() throws SQLException {

		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException("Driver not found");
			}

			String connectionUrl = String.format(
					"jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					DB_SERVER, port, DB_NAME);

			connection = DriverManager.getConnection(connectionUrl, user, password);
			// TODO remove
			System.out.println("Connected: " + connection);
		}
	}

	/**
	 * 
	 */
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				System.out.println("Disconnect from database.");
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
	}

	/**
	 * Saves/updates all persons from people in db.
	 * 
	 * @throws SQLException
	 */
	public void save() throws SQLException {

		// "?" represents a wild card
		String checkSql = "SELECT COUNT(*) AS count FROM people WHERE id=?";
		PreparedStatement checkStmt = null;

		String insertSql = "insert into people (id, name, age, employment_status, tax_id, us_citizen, gender, occupation) values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = null;

		String updateSql = "update people set name=?, age=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? where id=?";
		PreparedStatement updateStatement = null;

		try {
			checkStmt = connection.prepareStatement(checkSql);
			insertStatement = connection.prepareStatement(insertSql);
			updateStatement = connection.prepareStatement(updateSql);

			// iterate through all available people
			for (Person person : people) {
				int id = person.getId();
				String name = person.getName();
				AgeCategory age = person.getAgeCategory();
				EmploymentCategory emplCat = person.getEmpCat();
				String taxId = person.getTaxId();
				boolean isUs = person.isUsCitizen();
				Gender gender = person.getGender();
				String occupation = person.getOccupation();

				// substitute wildcard variable for checkStmt
				checkStmt.setInt(1, id);
				ResultSet checkResult = checkStmt.executeQuery();
				checkResult.next();

				int count = checkResult.getInt(1);

				if (count == 0) {
					System.out.println("Inserting person with ID " + id);

					int col = 1;
					insertStatement.setInt(col++, id);
					insertStatement.setString(col++, name);
					insertStatement.setString(col++, age.name());
					insertStatement.setString(col++, emplCat.name());
					insertStatement.setString(col++, taxId);
					insertStatement.setBoolean(col++, isUs);
					insertStatement.setString(col++, gender.name());
					insertStatement.setString(col, occupation);

					insertStatement.executeUpdate();
				} else {
					System.out.println("Updating person with ID " + id);

					int col = 1;
					updateStatement.setString(col++, name);
					updateStatement.setString(col++, age.name());
					updateStatement.setString(col++, emplCat.name());
					updateStatement.setString(col++, taxId);
					updateStatement.setBoolean(col++, isUs);
					updateStatement.setString(col++, gender.name());
					updateStatement.setString(col++, occupation);
					updateStatement.setInt(col, id);

					updateStatement.executeUpdate();
				}
			}
		} finally {
			try {
				checkStmt.close();
			} finally {
				try {
					insertStatement.close();
				} finally {
					updateStatement.close();
				}
			}
		}
	}

	/**
	 * Loads all available persons from db into person objects.
	 * 
	 * @throws SQLException
	 */
	public void load() throws SQLException {

		people.clear();

		Statement selectStatement = connection.createStatement();
		String sql = "select id, name, age, employment_status, tax_id, us_citizen, gender, occupation from people order by name";
		ResultSet results = selectStatement.executeQuery(sql);

		while (results.next()) {

			int id = results.getInt("id");
			String name = results.getString("name");
			String occ = results.getString("occupation");
			String age = results.getString("age");
			String emp = results.getString("employment_status");
			boolean isUs = results.getBoolean("us_citizen");
			String tax = results.getString("tax_id");
			String gender = results.getString("gender");

			Person person = new Person(id, name, occ, AgeCategory.valueOf(age), EmploymentCategory.valueOf(emp), isUs,
					tax, Gender.valueOf(gender));
			people.add(person);
		}

		results.close();
		selectStatement.close();

	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public void removePerson(int row) {
		people.remove(row);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}

	/**
	 * Write to a file
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Person[] persons = people.toArray(new Person[people.size()]);

		oos.writeObject(persons);
		oos.close();
	}

	/**
	 * Load objects from file
	 * 
	 * @param file
	 */
	public void loadFromFile(File file) throws IOException {

		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		// retrieve objects
		try {
			Person[] persons = (Person[]) ois.readObject();
			people.clear();
			people.addAll(Arrays.asList(persons));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
	}

}
