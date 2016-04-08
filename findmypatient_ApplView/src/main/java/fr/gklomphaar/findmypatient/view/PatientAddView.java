
package fr.gklomphaar.findmypatient.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.gklomphaar.findmypatient.controller.IdentityController;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Font;


public class PatientAddView extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDisplayName;
	private JTextField textFieldFName;
	private JTextField textFieldLName;
	private JTextField textFieldSSN;
	private JTextField textFieldPhone;
	private JTextField textFieldEmail;
	private JTextField textFieldDob;
	private JTextField textFieldRoom;

	/**
	 * Create the frame.
	 * @param identityController 
	 */
	public PatientAddView(final IdentityController controller) {
		
		setTitle("Add a patient");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFields = new JPanel();
		contentPane.add(panelFields, BorderLayout.SOUTH);
		
		JButton btnAddPatient = new JButton("Add patient");
		btnAddPatient.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.getPatientManagement().add( 
							new Patient(
									textFieldSSN.getText(),
									textFieldFName.getText(),
									textFieldLName.getText(),
									textFieldDob.getText(),
									textFieldPhone.getText(),
									textFieldEmail.getText(),
									textFieldDisplayName.getText(),
									textFieldRoom.getText()));
				} catch (NoAuthorityException e1) {
					JOptionPane.showMessageDialog(contentPane, "No authority to add a patient", "Add patient failed", JOptionPane.ERROR_MESSAGE);
				}
				catch ( DaoSaveObjectException e1) 
				{
					JOptionPane.showMessageDialog(contentPane, "Error while adding patient", "Add patient failed", JOptionPane.ERROR_MESSAGE);
				}
				dispose();
			}
		});
		panelFields.setLayout(new GridLayout(0, 1, 0, 0));
		panelFields.add(btnAddPatient);
		
		JPanel panelControl = new JPanel();
		contentPane.add(panelControl, BorderLayout.CENTER);
		GridBagLayout gbl_panelControl = new GridBagLayout();
		gbl_panelControl.columnWidths = new int[]{0, 0, 0};
		gbl_panelControl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelControl.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelControl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelControl.setLayout(gbl_panelControl);
		
		JLabel lblName = new JLabel("Display name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panelControl.add(lblName, gbc_lblName);
		
		textFieldDisplayName = new JTextField();
		GridBagConstraints gbc_textFieldDisplayName = new GridBagConstraints();
		gbc_textFieldDisplayName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDisplayName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDisplayName.gridx = 1;
		gbc_textFieldDisplayName.gridy = 0;
		panelControl.add(textFieldDisplayName, gbc_textFieldDisplayName);
		textFieldDisplayName.setColumns(10);
		
		JLabel lblRoom = new JLabel("First name");
		GridBagConstraints gbc_lblRoom = new GridBagConstraints();
		gbc_lblRoom.anchor = GridBagConstraints.EAST;
		gbc_lblRoom.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoom.gridx = 0;
		gbc_lblRoom.gridy = 1;
		panelControl.add(lblRoom, gbc_lblRoom);
		
		textFieldFName = new JTextField();
		GridBagConstraints gbc_textFieldFName = new GridBagConstraints();
		gbc_textFieldFName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldFName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFName.gridx = 1;
		gbc_textFieldFName.gridy = 1;
		panelControl.add(textFieldFName, gbc_textFieldFName);
		textFieldFName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last name");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 2;
		panelControl.add(lblLastName, gbc_lblLastName);
		
		textFieldLName = new JTextField();
		textFieldLName.setColumns(10);
		GridBagConstraints gbc_textFieldLName = new GridBagConstraints();
		gbc_textFieldLName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldLName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLName.gridx = 1;
		gbc_textFieldLName.gridy = 2;
		panelControl.add(textFieldLName, gbc_textFieldLName);
		
		JLabel lblSocialSecurityNumber = new JLabel("Social security number");
		GridBagConstraints gbc_lblSocialSecurityNumber = new GridBagConstraints();
		gbc_lblSocialSecurityNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblSocialSecurityNumber.anchor = GridBagConstraints.EAST;
		gbc_lblSocialSecurityNumber.gridx = 0;
		gbc_lblSocialSecurityNumber.gridy = 3;
		panelControl.add(lblSocialSecurityNumber, gbc_lblSocialSecurityNumber);
		
		textFieldSSN = new JTextField();
		textFieldSSN.setColumns(10);
		GridBagConstraints gbc_textFieldSSN = new GridBagConstraints();
		gbc_textFieldSSN.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSSN.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSSN.gridx = 1;
		gbc_textFieldSSN.gridy = 3;
		panelControl.add(textFieldSSN, gbc_textFieldSSN);
		
		JLabel lblPhoneNumber = new JLabel("Phone number");
		GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
		gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhoneNumber.anchor = GridBagConstraints.EAST;
		gbc_lblPhoneNumber.gridx = 0;
		gbc_lblPhoneNumber.gridy = 4;
		panelControl.add(lblPhoneNumber, gbc_lblPhoneNumber);
		
		textFieldPhone = new JTextField();
		textFieldPhone.setColumns(10);
		GridBagConstraints gbc_textFieldPhone = new GridBagConstraints();
		gbc_textFieldPhone.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPhone.gridx = 1;
		gbc_textFieldPhone.gridy = 4;
		panelControl.add(textFieldPhone, gbc_textFieldPhone);
		
		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 5;
		panelControl.add(lblEmail, gbc_lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.gridx = 1;
		gbc_textFieldEmail.gridy = 5;
		panelControl.add(textFieldEmail, gbc_textFieldEmail);
		
		JLabel lblDateOfBirth = new JLabel("Date of birth");
		GridBagConstraints gbc_lblDateOfBirth = new GridBagConstraints();
		gbc_lblDateOfBirth.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateOfBirth.anchor = GridBagConstraints.EAST;
		gbc_lblDateOfBirth.gridx = 0;
		gbc_lblDateOfBirth.gridy = 6;
		panelControl.add(lblDateOfBirth, gbc_lblDateOfBirth);
		
		textFieldDob = new JTextField();
		textFieldDob.setColumns(10);
		GridBagConstraints gbc_textFieldDob = new GridBagConstraints();
		gbc_textFieldDob.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDob.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDob.gridx = 1;
		gbc_textFieldDob.gridy = 6;
		panelControl.add(textFieldDob, gbc_textFieldDob);
		
		JLabel lblRoomNumber = new JLabel("Room number");
		GridBagConstraints gbc_lblRoomNumber = new GridBagConstraints();
		gbc_lblRoomNumber.anchor = GridBagConstraints.EAST;
		gbc_lblRoomNumber.insets = new Insets(0, 0, 0, 5);
		gbc_lblRoomNumber.gridx = 0;
		gbc_lblRoomNumber.gridy = 7;
		panelControl.add(lblRoomNumber, gbc_lblRoomNumber);
		
		textFieldRoom = new JTextField();
		textFieldRoom.setColumns(10);
		GridBagConstraints gbc_textFieldRoom = new GridBagConstraints();
		gbc_textFieldRoom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldRoom.gridx = 1;
		gbc_textFieldRoom.gridy = 7;
		panelControl.add(textFieldRoom, gbc_textFieldRoom);
	}

}
