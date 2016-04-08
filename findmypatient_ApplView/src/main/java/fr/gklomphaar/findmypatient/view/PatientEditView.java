/**
 * 
 */
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
import fr.gklomphaar.findmypatient.view.PatientOverviewTableModel;

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
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.BoxLayout;


public class PatientEditView extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldFName;
	private JTextField textFieldLName;
	private JTextField textFieldSSN;
	private JTextField textFieldPhone;
	private JTextField textFieldEmail;
	private JTextField textFieldRoom;
	private JTextField textFieldDob;
	private JTextField textFieldDisplay;
	private JTextField textFieldID;
	
	public PatientEditView(final IdentityController controller, final Patient patient, final PatientOverviewTableModel modelPatientOverview) {
		setTitle("Modifiy patient details");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 458, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel PatientFieldsPanel = new JPanel();
		contentPane.add(PatientFieldsPanel, BorderLayout.NORTH);
		GridBagLayout gbl_PatientFieldsPanel = new GridBagLayout();
		gbl_PatientFieldsPanel.columnWidths = new int[]{0, 0, 0};
		gbl_PatientFieldsPanel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_PatientFieldsPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_PatientFieldsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		PatientFieldsPanel.setLayout(gbl_PatientFieldsPanel);
		
		JLabel lblDisplayname = new JLabel("DisplayName");
		GridBagConstraints gbc_lblDisplayname = new GridBagConstraints();
		gbc_lblDisplayname.anchor = GridBagConstraints.EAST;
		gbc_lblDisplayname.insets = new Insets(0, 0, 5, 5);
		gbc_lblDisplayname.gridx = 0;
		gbc_lblDisplayname.gridy = 0;
		PatientFieldsPanel.add(lblDisplayname, gbc_lblDisplayname);
		
		textFieldDisplay = new JTextField();
		textFieldDisplay.setText(patient.getDisplayName());
		textFieldDisplay.setColumns(10);
		GridBagConstraints gbc_textFieldDisplay = new GridBagConstraints();
		gbc_textFieldDisplay.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDisplay.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDisplay.gridx = 1;
		gbc_textFieldDisplay.gridy = 0;
		PatientFieldsPanel.add(textFieldDisplay, gbc_textFieldDisplay);
		
		JLabel lblFirstName = new JLabel("First name");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.EAST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 1;
		PatientFieldsPanel.add(lblFirstName, gbc_lblFirstName);
		
		textFieldFName = new JTextField();
		textFieldFName.setColumns(10);
		textFieldFName.setText(patient.getfName());
		GridBagConstraints gbc_textFieldFName = new GridBagConstraints();
		gbc_textFieldFName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldFName.gridx = 1;
		gbc_textFieldFName.gridy = 1;
		PatientFieldsPanel.add(textFieldFName, gbc_textFieldFName);
		
		JLabel lblLastName = new JLabel("Last name");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 2;
		PatientFieldsPanel.add(lblLastName, gbc_lblLastName);
		
		textFieldLName = new JTextField();
		textFieldLName.setColumns(10);
		textFieldLName.setText(patient.getlName());
		GridBagConstraints gbc_textFieldLName = new GridBagConstraints();
		gbc_textFieldLName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldLName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLName.gridx = 1;
		gbc_textFieldLName.gridy = 2;
		PatientFieldsPanel.add(textFieldLName, gbc_textFieldLName);
		
		JLabel lblSocialSecurityNumber = new JLabel("SSN");
		GridBagConstraints gbc_lblSocialSecurityNumber = new GridBagConstraints();
		gbc_lblSocialSecurityNumber.anchor = GridBagConstraints.EAST;
		gbc_lblSocialSecurityNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblSocialSecurityNumber.gridx = 0;
		gbc_lblSocialSecurityNumber.gridy = 3;
		PatientFieldsPanel.add(lblSocialSecurityNumber, gbc_lblSocialSecurityNumber);
		
		textFieldSSN = new JTextField();
		textFieldSSN.setText(patient.getSsnNo());
		textFieldSSN.setColumns(10);
		GridBagConstraints gbc_textFieldSSN = new GridBagConstraints();
		gbc_textFieldSSN.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSSN.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSSN.gridx = 1;
		gbc_textFieldSSN.gridy = 3;
		PatientFieldsPanel.add(textFieldSSN, gbc_textFieldSSN);
		
		JLabel lblPhone = new JLabel("Phone");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.EAST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 4;
		PatientFieldsPanel.add(lblPhone, gbc_lblPhone);
		
		textFieldPhone = new JTextField();
		textFieldPhone.setText(patient.getCellNo());
		textFieldPhone.setColumns(10);
		GridBagConstraints gbc_textFieldPhone = new GridBagConstraints();
		gbc_textFieldPhone.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPhone.gridx = 1;
		gbc_textFieldPhone.gridy = 4;
		PatientFieldsPanel.add(textFieldPhone, gbc_textFieldPhone);
		
		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 5;
		PatientFieldsPanel.add(lblEmail, gbc_lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setText(patient.getEmail());
		textFieldEmail.setColumns(10);
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.gridx = 1;
		gbc_textFieldEmail.gridy = 5;
		PatientFieldsPanel.add(textFieldEmail, gbc_textFieldEmail);
		
		JLabel lblDob = new JLabel("Dob");
		GridBagConstraints gbc_lblDob = new GridBagConstraints();
		gbc_lblDob.anchor = GridBagConstraints.EAST;
		gbc_lblDob.insets = new Insets(0, 0, 5, 5);
		gbc_lblDob.gridx = 0;
		gbc_lblDob.gridy = 6;
		PatientFieldsPanel.add(lblDob, gbc_lblDob);
		
		textFieldDob = new JTextField();
		textFieldDob.setText(patient.getDob());
		textFieldDob.setColumns(10);
		GridBagConstraints gbc_textFieldDob = new GridBagConstraints();
		gbc_textFieldDob.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDob.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDob.gridx = 1;
		gbc_textFieldDob.gridy = 6;
		PatientFieldsPanel.add(textFieldDob, gbc_textFieldDob);
		
		JLabel label_5 = new JLabel("Room");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 7;
		PatientFieldsPanel.add(label_5, gbc_label_5);
		
		textFieldRoom = new JTextField();
		textFieldRoom.setText(patient.getroomNo());
		textFieldRoom.setColumns(10);
		GridBagConstraints gbc_textFieldRoom = new GridBagConstraints();
		gbc_textFieldRoom.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldRoom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldRoom.gridx = 1;
		gbc_textFieldRoom.gridy = 7;
		PatientFieldsPanel.add(textFieldRoom, gbc_textFieldRoom);
		
		JLabel lblId = new JLabel("ID");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 8;
		PatientFieldsPanel.add(lblId, gbc_lblId);
		
		textFieldID = new JTextField();
		textFieldID.setEnabled(false);
		textFieldID.setText(patient.getpId());
		textFieldID.setColumns(10);
		GridBagConstraints gbc_textFieldID = new GridBagConstraints();
		gbc_textFieldID.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldID.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldID.gridx = 1;
		gbc_textFieldID.gridy = 8;
		PatientFieldsPanel.add(textFieldID, gbc_textFieldID);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(40, 0));
		
		JButton btnDeletePatient = new JButton("Delete patient");
		btnDeletePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.getPatientManagement().delete(patient);
				} catch (NoAuthorityException e1) {
					JOptionPane.showMessageDialog(contentPane, "No authority to delete patient", "delete failed", JOptionPane.ERROR_MESSAGE);
				} catch (DaoSaveObjectException e1) {
					JOptionPane.showMessageDialog(contentPane, "Error while deleting patient", "delete failed", JOptionPane.ERROR_MESSAGE);
				}
				
				// Update the view and close
				modelPatientOverview.fireTableDataChanged();  
				dispose();
			}
		});
		btnDeletePatient.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(btnDeletePatient, BorderLayout.WEST);
		
		JButton btnModify = new JButton("Update changes");
		panel_1.add(btnModify, BorderLayout.CENTER);
		btnModify.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Retrieve the values
				patient.setSsnNo(textFieldSSN.getText());
				patient.setfName(textFieldFName.getText());
				patient.setlName(textFieldLName.getText());
				patient.setDob(textFieldDob.getText());
				patient.setDisplayName(textFieldDisplay.getText());
				patient.setCellNo(textFieldPhone.getText());
				patient.setEmail(textFieldEmail.getText());
				patient.setroomNo(textFieldRoom.getText());
				
				
				// Modify the patient
				try {
					controller.getPatientManagement().modify(patient);
				} catch (NoAuthorityException e1) {
					JOptionPane.showMessageDialog(contentPane, "No authority to delete patient", "delete failed", JOptionPane.ERROR_MESSAGE);
				} catch (DaoSaveObjectException e1) {
					JOptionPane.showMessageDialog(contentPane, "Error while saving patient", "save failed", JOptionPane.ERROR_MESSAGE);
				}
				
				// Update the view and close
				modelPatientOverview.fireTableDataChanged();  
				dispose();
			}
		});
	}

}
