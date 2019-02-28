package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * 
 * 
 */
public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel occupationLabel;
	private JTextField occupationField;
	private JList<AgeCategory> ageList;
	private JComboBox<String> empCombo;
	private JCheckBox citizenCheck;
	private JLabel taxLabel;
	private JTextField taxField;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	private JButton okButton;

	private FormListener formListener;

	/**
	 * Constructor
	 */
	public FormPanel() {

		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);

		nameLabel = new JLabel("Name: ");
		nameField = new JTextField(10);
		occupationLabel = new JLabel("Occupation: ");
		occupationField = new JTextField(10);
		ageList = new JList<>();
		empCombo = new JComboBox<>();
		citizenCheck = new JCheckBox();
		taxLabel = new JLabel("Tax ID: ");
		taxField = new JTextField(10);
		okButton = new JButton("OK");

		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);
		
		// radio buttons
		maleRadio = new JRadioButton("male");
		femaleRadio = new JRadioButton("female");
		
		setUpAgeList();
		setUpEmploymentComboBox();
		setUpUsCitizenInfo();
		setUpGenderRadioButtons();
		setUpOkButton();

		Border innerBorder = BorderFactory.createTitledBorder("Add Person");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutComponents();
	} // Constructor

	private void setUpAgeList() {
		// Set up ageList
		DefaultListModel<AgeCategory> ageModel = new DefaultListModel<>();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "From 18 to 65"));
		ageModel.addElement(new AgeCategory(2, "Above 65"));
		ageList.setModel(ageModel);

		ageList.setPreferredSize(new Dimension(110, 68));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(1);
	}

	private void setUpEmploymentComboBox() {
		// Set up combo box model
		DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<>();
		empModel.addElement("employed");
		empModel.addElement("self-employed");
		empModel.addElement("unemployed");
		empCombo.setModel(empModel);
		empCombo.setSelectedIndex(0);
		empCombo.setEditable(true);
	}
	
	private void setUpUsCitizenInfo() {
		citizenCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isTicked = citizenCheck.isSelected();
				taxLabel.setEnabled(isTicked);
				taxField.setEnabled(isTicked);
			}
		});
		
		// setup Tax id
		taxLabel.setEnabled(false);
		taxField.setEnabled(false);
	}
	
	private void setUpGenderRadioButtons() {
		genderGroup = new ButtonGroup();

		maleRadio.setSelected(true);
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");

		// set up radio buttons group
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
	}

	private void setUpOkButton() {

		okButton.setMnemonic(KeyEvent.VK_O);
		// button action listener
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// accessing several widget properties
				String name = nameField.getText();
				String occupation = occupationField.getText();
				int ageCat = ((AgeCategory) ageList.getSelectedValue()).getId();
				String empCat = (String) empCombo.getSelectedItem();
				// tax
				String taxId = taxField.getText();
				boolean isUsCitizen = citizenCheck.isSelected();

				String gender = genderGroup.getSelection().getActionCommand();

				// generating a form event
				FormEvent formEvent = new FormEvent(this, name, occupation, ageCat, empCat, isUsCitizen, taxId, gender);

				if (formListener != null) {
					formListener.formEventOccurred(formEvent);
				}
			}
		});
	}

	/**
	 * 
	 */
	private void layoutComponents() {
		// GridBagLayout
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		/////// First row ///////
		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(nameLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);

		/////// next row ///////
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(occupationLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField, gc);

		/////// next row ///////
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Age: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(ageList, gc);

		/////// next row ///////
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Emplyoment: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(empCombo, gc);

		/////// next row ///////
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("US Citizen: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(citizenCheck, gc);

		/////// next row ///////
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(taxLabel, gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(taxField, gc);

		/////// next row ///////
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.05;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Gender: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(maleRadio, gc);

		/////// next row ///////
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(femaleRadio, gc);

		/////// next row ///////
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 2.0;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(okButton, gc);
	}

	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
}

/**
 * Inner class
 */
class AgeCategory {

	private int id;
	private String text;

	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return text;
	}

}