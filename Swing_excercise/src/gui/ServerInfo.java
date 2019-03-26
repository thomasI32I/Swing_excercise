package gui;

public class ServerInfo {

	private String name;
	private int id;
	
	private boolean isChecked;
	
	/**
	 * Constructor
	 * 
	 * @param name Name of the server.
	 * @param id
	 * @param checked
	 */
	public ServerInfo(String name, int id, boolean checked) {
		this.name = name;
		this.id = id;
		this.isChecked = checked;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}
