
package fr.gklomphaar.findmypatient.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.GridLayout;

import fr.gklomphaar.findmypatient.controller.IdentityController;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient.helpers.IMatcher;
import fr.gklomphaar.findmypatient.view.PatientOverviewTableModel;
import fr.gklomphaar.findmypatient.view.PatientSearchComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

/**
 * @author Gerard
 *
 */
@SuppressWarnings("serial")
public class PatientOverviewView extends JFrame implements ActionListener
{
	IdentityController identityController;

	private JPanel contentPane;
	private JTable tablePatients;
	
	private JButton btnListAllPatients;
	private JButton btnAddNewPatient;
	private PatientOverviewTableModel modelPatientOverview; 
	
	private PatientAddView patientAddView;
	private JTextField textFieldSearchName;

	/**
	 * Create the frame.
	 * @param identityController 
	 */
	public PatientOverviewView(IdentityController identityController) 
	{
		this.identityController = identityController;
		createComponents();
	}

	private void createComponents()
	{
		
		setTitle("Patient Overview");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelHeaderSearch = new JPanel();
		contentPane.add(panelHeaderSearch, BorderLayout.NORTH);
		panelHeaderSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		final PatientSearchComboBoxModel modelPatientSearch = new PatientSearchComboBoxModel();
		
		JButton btnSearch = new JButton("SearchPatient");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get the selected matcher
				IMatcher<Patient> matcher = (IMatcher<Patient>)modelPatientSearch.getSelectedItem();
				
				// Retrieve the patients
				java.util.List<Patient> allPatients = null;
				try {
					Patient patient = new Patient(
							textFieldSearchName.getText(),
							textFieldSearchName.getText(),
							textFieldSearchName.getText(),
							textFieldSearchName.getText(),
							textFieldSearchName.getText(),
							textFieldSearchName.getText(),
							textFieldSearchName.getText(),
							textFieldSearchName.getText());
					allPatients = identityController.getPatientManagement().search(patient, matcher);
				} catch (NoAuthorityException | DaoLoadObjectException e1) {
					e1.printStackTrace();
				}
				
				//Update the table list
				modelPatientOverview.setPatients(allPatients);
				
			}
		});
		panelHeaderSearch.add(btnSearch);
		JComboBox comboBox = new JComboBox(modelPatientSearch);
		panelHeaderSearch.add(comboBox);
		
		textFieldSearchName = new JTextField();
		panelHeaderSearch.add(textFieldSearchName);
		textFieldSearchName.setColumns(40);
		
		JPanel panelCommands = new JPanel();
		contentPane.add(panelCommands, BorderLayout.SOUTH);
		panelCommands.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelGet = new JPanel();
		panelCommands.add(panelGet);
				panelGet.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
				
				this.btnListAllPatients = new JButton("List all patients");
				panelGet.add(btnListAllPatients);
				btnListAllPatients.addActionListener(this);
		
		JPanel panelAdd = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelAdd.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelCommands.add(panelAdd);
		
		this.btnAddNewPatient = new JButton("Add new patient");
		panelAdd.add(btnAddNewPatient);
		this.btnAddNewPatient.addActionListener(this);
		
		this.modelPatientOverview = new PatientOverviewTableModel();
		
		JPanel panelData = new JPanel();
		contentPane.add(panelData, BorderLayout.CENTER);
		panelData.setLayout(new GridLayout(0, 1, 0, 0));
		tablePatients = new JTable(this.modelPatientOverview);
		tablePatients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablePatients.rowAtPoint(e.getPoint());
		        if(row >= 0)
		        {
					Patient patient = modelPatientOverview.getPatient(row);
					PatientEditView patientEditeView = new PatientEditView(identityController, patient, modelPatientOverview);
					patientEditeView.setVisible(true);
		        }
			}
		});
		tablePatients.setFillsViewportHeight(true);
		
		JScrollPane scrollPanePatients = new JScrollPane(tablePatients);
		panelData.add(scrollPanePatients);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if(source.equals(btnListAllPatients))
		{
			// Retrieve the patients
			java.util.List<Patient> allPatients = null;
			try {
				allPatients = this.identityController.getPatientManagement().readAll();
			} catch (NoAuthorityException | DaoLoadObjectException e1) {
				// catch block
				e1.printStackTrace();
			}
			
			//Update the table list
			modelPatientOverview.setPatients(allPatients);
			
		}
		else if(source.equals(btnAddNewPatient))
		{
			if(patientAddView == null)
			{
				patientAddView = new PatientAddView(this.identityController);
			}
			patientAddView.setVisible(true);
		}
	}
}
