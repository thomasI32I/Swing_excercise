package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {

	private final static String ROOT_USER = "root";
	private final static String ROOT_USER_PW = "Tomislaw33!!!";
	private final static String DB_SERVER = "localhost";
	private final static String DB_SERVER_PORT = "3306";
	private final static String DB_NAME = "world";
	
	private LinkedList<Person> people;
	
	Connection connection;

	public Database() {
		people = new LinkedList<>();
	}

	public void connect() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}
		
		String connectionUrl = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				DB_SERVER,
				DB_SERVER_PORT,
				DB_NAME);
		connection = DriverManager.getConnection(connectionUrl, ROOT_USER, ROOT_USER_PW);
		System.out.println("Connected: " + connection);
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
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
